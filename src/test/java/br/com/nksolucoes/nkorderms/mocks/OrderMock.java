package br.com.nksolucoes.nkorderms.mocks;

import br.com.nksolucoes.nkorderms.domain.enums.OrderStatusEnum;
import br.com.nksolucoes.nkorderms.domain.model.Customer;
import br.com.nksolucoes.nkorderms.domain.model.Item;
import br.com.nksolucoes.nkorderms.domain.model.Order;

import java.math.BigDecimal;
import java.util.List;

public class OrderMock {

	public static Order createOrderMock() {
		Order order = new Order();
		order.setOrderId(1L);
		order.setUniqueHash("mockHash123");
		order.setOrderStatus(OrderStatusEnum.CREATED);
		order.setCustomer(CustomerMock.createCustomerMock());
		order.setItems(List.of(ItemMock.createItemMock()));
		order.setTotalAmount(new BigDecimal("100.00"));
		return order;
	}

	public static Order createOrderWithSpecificValues(Long id, String uniqueHash, BigDecimal totalAmount) {
		Order order = new Order();
		order.setOrderId(id);
		order.setUniqueHash(uniqueHash);
		order.setOrderStatus(OrderStatusEnum.CREATED);
		order.setCustomer(CustomerMock.createCustomerMock());
		order.setItems(List.of(ItemMock.createItemMock()));
		order.setTotalAmount(totalAmount);
		return order;
	}
}
