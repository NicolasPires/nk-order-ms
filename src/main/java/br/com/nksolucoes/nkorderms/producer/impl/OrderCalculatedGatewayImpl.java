package br.com.nksolucoes.nkorderms.producer.impl;

import br.com.nksolucoes.nkorderms.config.AppConfig;
import br.com.nksolucoes.nkorderms.domain.mapper.OrderMapper;
import br.com.nksolucoes.nkorderms.domain.model.Order;
import br.com.nksolucoes.nkorderms.domain.records.response.OrderResponse;
import br.com.nksolucoes.nkorderms.producer.OrderCalculatedGateway;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderCalculatedGatewayImpl implements OrderCalculatedGateway {

	private static Logger LOGGER = LoggerFactory.getLogger(OrderCalculatedGatewayImpl.class);

	@Autowired
	private StreamBridge streamBridge;

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private OrderMapper orderMapper;

	@Override
	public void sendCalculatedOrderEvent(Order order) {
		LOGGER.info("Sending calculated order event. Order: {}", order.getOrderId());
		OrderResponse orderResponse = orderMapper.entityToResponse(order);
		streamBridge.send(appConfig.getOrderCalculatedChannel(), orderResponse);
	}
}
