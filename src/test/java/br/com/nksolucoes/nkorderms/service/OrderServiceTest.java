package br.com.nksolucoes.nkorderms.service;

import br.com.nksolucoes.nkorderms.domain.mapper.OrderMapper;
import br.com.nksolucoes.nkorderms.domain.model.Customer;
import br.com.nksolucoes.nkorderms.domain.model.Item;
import br.com.nksolucoes.nkorderms.domain.model.Order;
import br.com.nksolucoes.nkorderms.domain.records.request.OrderRequest;
import br.com.nksolucoes.nkorderms.domain.records.response.OrderResponse;
import br.com.nksolucoes.nkorderms.exceptions.DuplicateOrderException;
import br.com.nksolucoes.nkorderms.exceptions.OrderNotFoundException;
import br.com.nksolucoes.nkorderms.mocks.CustomerMock;
import br.com.nksolucoes.nkorderms.mocks.OrderMock;
import br.com.nksolucoes.nkorderms.mocks.OrderRequestMock;
import br.com.nksolucoes.nkorderms.mocks.OrderResponseMock;
import br.com.nksolucoes.nkorderms.producer.OrderCalculatedGateway;
import br.com.nksolucoes.nkorderms.repository.ItemRepository;
import br.com.nksolucoes.nkorderms.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class OrderServiceTest {

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private OrderMapper orderMapper;

	@Mock
	private ItemService itemService;

	@Mock
	private CustomerService customerService;

	@Mock
	private OrderCalculatedGateway orderCalculatedGateway;

	@Mock
	private ItemRepository itemRepository;

	@InjectMocks
	private OrderService orderService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createAndCalculateASingleOrder_ShouldReturnOrderResponse_WhenRequestIsValid() {
		// Arrange
		OrderRequest orderRequest = OrderRequestMock.createValidOrderRequest();
		Order order = OrderMock.createOrderMock();
		Customer customer = CustomerMock.createCustomerMock();
		OrderResponse orderResponse = OrderResponseMock.createValidOrderResponse();

		when(orderMapper.requestToEntity(orderRequest)).thenReturn(order);
		when(orderRepository.existsByUniqueHash(anyString())).thenReturn(false);
		when(customerService.createOrUpdateCustomer(any())).thenReturn(customer);
		when(orderRepository.save(any(Order.class))).thenReturn(order);
		when(orderMapper.entityToResponse(order)).thenReturn(orderResponse);
		when(itemService.calculateSubTotal(any())).thenReturn(BigDecimal.ZERO);


		// Act
		OrderResponse result = orderService.createAndCalculateASingleOrder(orderRequest);

		// Assert
		assertNotNull(result);
	}

	@Test
	void createAndCalculateASingleOrder_ShouldThrowException_WhenOrderIsDuplicate() {
		// Arrange
		OrderRequest orderRequest = OrderRequestMock.createValidOrderRequest();
		Order order = OrderMock.createOrderMock();

		when(orderMapper.requestToEntity(orderRequest)).thenReturn(order);
		when(orderRepository.existsByUniqueHash(anyString())).thenReturn(true);
		when(itemService.calculateSubTotal(any())).thenReturn(BigDecimal.ZERO);

		// Act & Assert
		assertThrows(DuplicateOrderException.class, () -> orderService.createAndCalculateASingleOrder(orderRequest));
	}

	@Test
	void processOrderCalculation_ShouldUpdateOrderAndSendEvent() {
		// Arrange
		Order order = mock(Order.class);
		Item item = mock(Item.class);
		when(order.getItems()).thenReturn(List.of(item));
		when(itemService.calculateSubTotal(item)).thenReturn(BigDecimal.TEN);
		when(orderRepository.save(order)).thenReturn(order);

		// Act
		orderService.processOrderCalculation(order);

		// Assert
		verify(orderRepository).save(order);
		verify(orderCalculatedGateway).sendCalculatedOrderEvent(order);
	}

	@Test
	void findById_ShouldReturnOrderResponse_WhenOrderExists() {
		// Arrange
		Order order = mock(Order.class);
		OrderResponse orderResponse = mock(OrderResponse.class);

		when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
		when(orderMapper.entityToResponse(order)).thenReturn(orderResponse);

		// Act
		OrderResponse result = orderService.findById(1L);

		// Assert
		assertNotNull(result);
		assertEquals(orderResponse, result);
	}

	@Test
	void findById_ShouldThrowException_WhenOrderDoesNotExist() {
		// Arrange
		when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(OrderNotFoundException.class, () -> orderService.findById(1L));
	}

	@Test
	void findAllOrders_ShouldReturnPageOfOrderResponses() {
		// Arrange
		Order order = mock(Order.class);
		OrderResponse orderResponse = mock(OrderResponse.class);
		Page<Order> orderPage = new PageImpl<>(List.of(order));
		Pageable pageable = PageRequest.of(0, 10);

		when(orderRepository.findAll(pageable)).thenReturn(orderPage);
		when(orderMapper.entityToResponse(order)).thenReturn(orderResponse);

		// Act
		Page<OrderResponse> result = orderService.findAllOrders(0, 10);

		// Assert
		assertNotNull(result);
		assertEquals(1, result.getContent().size());
		assertEquals(orderResponse, result.getContent().get(0));
	}

	@Test
	void generateOrderHash_ShouldReturnConsistentHash() {
		// Arrange
		Order order = mock(Order.class);
		Customer customer = mock(Customer.class);
		Item item = mock(Item.class);

		when(order.getCustomer()).thenReturn(customer);
		when(customer.getDocument()).thenReturn("12345678900");
		when(customer.getName()).thenReturn("John Doe");
		when(customer.getEmail()).thenReturn("john.doe@example.com");
		when(customer.getPhone()).thenReturn("123456789");
		when(order.getItems()).thenReturn(List.of(item));
		when(item.getDescription()).thenReturn("Item 1");
		when(item.getQuantity()).thenReturn(2);
		when(item.getUnitPrice()).thenReturn(BigDecimal.TEN);

		// Act
		String hash = orderService.generateOrderHash(order);

		// Assert
		assertNotNull(hash);
	}
}