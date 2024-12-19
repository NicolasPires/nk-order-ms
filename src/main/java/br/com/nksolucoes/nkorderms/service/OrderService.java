package br.com.nksolucoes.nkorderms.service;

import br.com.nksolucoes.nkorderms.domain.enums.OrderStatusEnum;
import br.com.nksolucoes.nkorderms.domain.mapper.OrderMapper;
import br.com.nksolucoes.nkorderms.domain.model.Customer;
import br.com.nksolucoes.nkorderms.domain.model.Item;
import br.com.nksolucoes.nkorderms.domain.model.Order;
import br.com.nksolucoes.nkorderms.domain.records.request.OrderRequest;
import br.com.nksolucoes.nkorderms.domain.records.response.OrderResponse;
import br.com.nksolucoes.nkorderms.exceptions.DuplicateOrderException;
import br.com.nksolucoes.nkorderms.exceptions.OrderNotFoundException;
import br.com.nksolucoes.nkorderms.producer.OrderCalculatedGateway;
import br.com.nksolucoes.nkorderms.repository.OrderRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final OrderMapper orderMapper;
	private final ItemService itemService;
	private final CustomerService customerService;
	private final OrderCalculatedGateway orderCalculatedGateway;

	@Transactional
	public OrderResponse createAndCalculateASingleOrder(OrderRequest orderRequest) {
		log.info("Processing order request for customer document: {}", orderRequest.customer());

		Order order = orderMapper.requestToEntity(orderRequest);
		order.setUniqueHash(generateOrderHash(order));

		if (orderRepository.existsByUniqueHash(order.getUniqueHash())) {
			throw new DuplicateOrderException("Order already in the database! please modify your order. uniqueHash: " + order.getUniqueHash());
		}

		Customer customer = customerService.createOrUpdateCustomer(orderRequest.customer());
		order.setCustomer(customer);

		order.getItems().forEach(item -> {
			item.setOrder(order);
			item.setSubtotal(BigDecimal.ZERO);
		});

		order.setOrderStatus(OrderStatusEnum.CREATED);
		order.setTotalAmount(BigDecimal.ZERO);

		Order savedOrder = orderRepository.save(order);
		log.info("Order created with ID: {}", savedOrder.getOrderId());

		calculateOrdersAsync(List.of(savedOrder));

		return orderMapper.entityToResponse(savedOrder);
	}

	@Async
	public void calculateOrdersAsync(List<Order> ordersToCalculate) {
		log.info("Calculating orders asynchronously...");
		ordersToCalculate.forEach(this::processOrderCalculation);
	}

	@Transactional
	public void processOrderCalculation(Order order) {
		log.info("Calculating total amount for Order ID: {}", order.getOrderId());

		BigDecimal totalAmount = order.getItems().stream()
				.map(itemService::calculateSubTotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		order.setTotalAmount(totalAmount);
		order.setOrderStatus(OrderStatusEnum.CALCULATED);

		Order updatedOrder = orderRepository.save(order);
		orderCalculatedGateway.sendCalculatedOrderEvent(updatedOrder);

		log.info("Order ID: {} calculated. Total Amount: {}", updatedOrder.getOrderId(), totalAmount);
	}

	public OrderResponse findById(Long id) {
		return orderRepository.findById(id)
				.map(orderMapper::entityToResponse)
				.orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
	}

	public Page<OrderResponse> findAllOrders(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Order> orders = orderRepository.findAll(pageable);
		return orders.map(orderMapper::entityToResponse);
	}

	public String generateOrderHash(Order order) {
		String hashSource = order.getCustomer().getDocument() + ":" +
				order.getCustomer().getName() +
				order.getCustomer().getEmail() +
				order.getCustomer().getPhone() +
				order.getItems().stream()
						.sorted((item1, item2) -> item1.getDescription().compareTo(item2.getDescription()))
						.map(item -> item.getDescription() + ":" +
								item.getQuantity() + ":" +
								item.getUnitPrice())
						.collect(Collectors.joining("|"));
		return DigestUtils.md5Hex(hashSource.getBytes());
	}
}
