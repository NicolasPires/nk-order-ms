package br.com.nksolucoes.nkorderms.controller;

import br.com.nksolucoes.nkorderms.domain.model.Order;
import br.com.nksolucoes.nkorderms.domain.records.request.OrderRequest;
import br.com.nksolucoes.nkorderms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	public Order processOrder(@RequestBody OrderRequest orderRequest) {
		return orderService.createAndCalculateASingleOrder(orderRequest);
	}

}
