package br.com.nksolucoes.nkorderms.service;

import br.com.nksolucoes.nkorderms.domain.enums.OrderStatusEnum;
import br.com.nksolucoes.nkorderms.domain.mapper.OrderMapper;
import br.com.nksolucoes.nkorderms.domain.model.Order;
import br.com.nksolucoes.nkorderms.domain.records.request.OrderRequest;
import br.com.nksolucoes.nkorderms.domain.records.response.OrderResponse;
import br.com.nksolucoes.nkorderms.producer.OrderCalculatedGateway;
import br.com.nksolucoes.nkorderms.repository.OrderRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class OrderService {

	private static Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private ItemService itemService;

	@Autowired
	private OrderCalculatedGateway orderCalculatedGateway;

	public OrderResponse createAndCalculateASingleOrder(OrderRequest orderRequest) {
		LOGGER.info("Order request processing started");

		Order order = orderMapper.requestToEntity(orderRequest);
		order.getItems().forEach(item -> {
			item.setOrder(order);
			item.setSubtotal(BigDecimal.ZERO);
		});
		order.setOrderStatus(OrderStatusEnum.CREATED);
		order.setTotalAmount(BigDecimal.ZERO);

		Order savedOrder = orderRepository.save(order);

		LOGGER.info("Order creation completed. OrderId: {}", savedOrder.getOrderId());

		List<Order> ordersToProcess = new ArrayList<>();
		ordersToProcess.add(savedOrder);

		CompletableFuture.runAsync(() -> {
			calculateOrders(ordersToProcess);
		});

		OrderResponse orderResponse = orderMapper.entityToResponse(savedOrder);

    	return orderResponse;
	}

	@Synchronized
	public void calculateOrders(List<Order> ordersToCalculate) {
		LOGGER.info("Calculating orders and sending complete orders to the queue");
		ordersToCalculate.forEach(order -> {
			LOGGER.info("Starting the order calculation process. OrderId: {}", order.getOrderId());

			BigDecimal totalAmount = order.getItems().stream()
					.map(itemService::calculateSubTotalOrderItem)
					.reduce(BigDecimal.ZERO, BigDecimal::add);

			order.setTotalAmount(BigDecimal.valueOf(totalAmount.doubleValue()));

			LOGGER.info("OrderId: {}, Total Amount Calculated: {}", order.getOrderId(), totalAmount);

			order.setOrderStatus(OrderStatusEnum.CALCULATED);
			Order updatedOrder = orderRepository.save(order);

			orderCalculatedGateway.sendCalculatedOrderEvent(updatedOrder);
		});
	}

	public OrderResponse findById(Long id) {
		 return orderRepository.findById(id).map(orderMapper::entityToResponse).orElseThrow(() ->
			 new RuntimeException("Order not found"));
	}

	public Page<OrderResponse> findAllOrders(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Order> orders = orderRepository.findAll(pageable);
		return orders.map(orderMapper::entityToResponse);
	}
}
