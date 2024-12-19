package br.com.nksolucoes.nkorderms.mocks;

import br.com.nksolucoes.nkorderms.domain.enums.OrderStatusEnum;
import br.com.nksolucoes.nkorderms.domain.model.Item;
import br.com.nksolucoes.nkorderms.domain.records.response.CustomerResponse;
import br.com.nksolucoes.nkorderms.domain.records.response.ItemResponse;
import br.com.nksolucoes.nkorderms.domain.records.response.OrderResponse;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponseMock {

	public static OrderResponse createValidOrderResponse() {

		ItemResponse itemResponse = new ItemResponse(
				1L,
				"item-mock",
				1,
				BigDecimal.ONE,
				BigDecimal.ONE
		);

		CustomerResponse customerResponse = new CustomerResponse(
				1L,
				"12345678900",
				"Mock Customer",
				"Mock Street",
				"559299745662",
				"mock@mock.com"
		);

		return new OrderResponse(
				1L,
				customerResponse,
				OrderStatusEnum.CREATED,
				List.of(itemResponse),
				"notes-mock",
				BigDecimal.ONE
		);
	}
}
