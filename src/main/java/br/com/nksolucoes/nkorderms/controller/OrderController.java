package br.com.nksolucoes.nkorderms.controller;

import br.com.nksolucoes.nkorderms.domain.records.request.OrderRequest;
import br.com.nksolucoes.nkorderms.domain.records.response.OrderResponse;
import br.com.nksolucoes.nkorderms.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/order", produces = {"application/json"})
@Tag(name = "order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Operation(summary = "Sends the information to be processed by the API", method = "POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Order sent successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid parameters")
	})
	@PostMapping
	public OrderResponse processOrder(@RequestBody OrderRequest orderRequest) {
		return orderService.createAndCalculateASingleOrder(orderRequest);
	}

	@Operation(summary = "Search the Order by ID", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
			@ApiResponse(responseCode = "404", description = "Order not found")
	})
	@GetMapping
	public OrderResponse findOrderById(@RequestParam(name = "orderId") Long orderId) {
		return orderService.findById(orderId);
	}

	@Operation(summary = "Search all available Orders", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok")
	})
	@GetMapping("/all")
	public Page<OrderResponse> getAllOrders(Pageable pageable) {
		return orderService.findAllOrders(pageable);
	}

}
