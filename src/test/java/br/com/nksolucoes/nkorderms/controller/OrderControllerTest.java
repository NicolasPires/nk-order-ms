package br.com.nksolucoes.nkorderms.controller;

import br.com.nksolucoes.nkorderms.domain.records.response.OrderResponse;
import br.com.nksolucoes.nkorderms.mocks.OrderResponseMock;
import br.com.nksolucoes.nkorderms.service.OrderService;
import br.com.nksolucoes.nkorderms.utils.TestJsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest {

	@Mock
	private OrderService orderService;

	@InjectMocks
	private OrderController orderController;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
	}

	@Test
	void processOrder_ShouldReturnOrderResponse_WhenRequestIsValid() throws Exception {
		OrderResponse mockResponse = OrderResponseMock.createValidOrderResponse();
		when(orderService.createAndCalculateASingleOrder(any()))
				.thenReturn(mockResponse);

		String orderRequestJson = TestJsonUtils.orderRequestWithCustomCustomer(mockResponse.customer().document(),
				mockResponse.customer().name(),
				mockResponse.customer().email(),
				mockResponse.customer().phone());

		mockMvc.perform(post("/order")
						.contentType(MediaType.APPLICATION_JSON)
						.content(orderRequestJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.orderId").value(mockResponse.orderId()))
				.andExpect(jsonPath("$.customer.customerId").value(mockResponse.customer().customerId()));
	}

	@Test
	void findOrderById_ShouldReturnOrderResponse_WhenOrderExists() throws Exception {
		OrderResponse mockResponse = OrderResponseMock.createValidOrderResponse();
		when(orderService.findById(anyLong())).thenReturn(mockResponse);

		mockMvc.perform(get("/order")
						.param("orderId", "1")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.orderId").value(mockResponse.orderId()))
				.andExpect(jsonPath("$.customer.customerId").value(mockResponse.customer().customerId()));
	}

	@Test
	void getAllOrders_ShouldReturnPageOfOrders() throws Exception {
		Page<OrderResponse> mockPage = new PageImpl<>(
				List.of(
						OrderResponseMock.createValidOrderResponse(),
						OrderResponseMock.createValidOrderResponse()
				),
				PageRequest.of(0, 2), 2);

		when(orderService.findAllOrders(anyInt(), anyInt())).thenReturn(mockPage);

		mockMvc.perform(get("/order/all")
						.param("page", "0")
						.param("size", "2")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(
						jsonPath("$.content[0].orderId").value(mockPage.getContent().get(0).orderId()));
	}
}
