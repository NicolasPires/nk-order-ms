package br.com.nksolucoes.nkorderms.controller;

import br.com.nksolucoes.nkorderms.domain.model.Order;
import br.com.nksolucoes.nkorderms.domain.records.request.OrderRequest;
import br.com.nksolucoes.nkorderms.domain.records.response.OrderResponse;
import br.com.nksolucoes.nkorderms.service.OrderService;
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
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	public OrderResponse processOrder(@RequestBody OrderRequest orderRequest) {
		return orderService.createAndCalculateASingleOrder(orderRequest);
	}

	@GetMapping
	public OrderResponse findOrderById(@RequestParam(name = "orderId") Long orderId) {
		return orderService.findById(orderId);
	}

	@GetMapping("/all")
	public Page<OrderResponse> getAllOrders(Pageable pageable) {
		return orderService.findAllOrders(pageable);
	}


}
