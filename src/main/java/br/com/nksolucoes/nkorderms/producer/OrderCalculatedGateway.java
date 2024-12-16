package br.com.nksolucoes.nkorderms.producer;

import br.com.nksolucoes.nkorderms.domain.model.Order;

public interface OrderCalculatedGateway {
	void sendCalculatedOrderEvent(Order order);
}
