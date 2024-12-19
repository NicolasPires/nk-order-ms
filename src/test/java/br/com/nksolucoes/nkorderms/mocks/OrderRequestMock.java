package br.com.nksolucoes.nkorderms.mocks;

import br.com.nksolucoes.nkorderms.domain.records.request.CustomerRequest;
import br.com.nksolucoes.nkorderms.domain.records.request.ItemRequest;
import br.com.nksolucoes.nkorderms.domain.records.request.OrderRequest;

import java.math.BigDecimal;
import java.util.List;

public class OrderRequestMock {

	public static OrderRequest createValidOrderRequest() {
		CustomerRequest customerRequest = new CustomerRequest(
				"12345678900",
				"Mock Customer",
				"street-of-mock, 10",
				"559299745662",
				"email-mock@mock.com.br"
		);

		ItemRequest itemRequest = new ItemRequest(
				"Mock Item",
				2,
				BigDecimal.TEN
		);

		return new OrderRequest(
				customerRequest,
				"notes-mock",
				List.of(itemRequest)
		);
	}
}
