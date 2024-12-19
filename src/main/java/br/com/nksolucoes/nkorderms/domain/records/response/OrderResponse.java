package br.com.nksolucoes.nkorderms.domain.records.response;

import br.com.nksolucoes.nkorderms.domain.enums.OrderStatusEnum;
import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(Long orderId,
							CustomerResponse customer,
							OrderStatusEnum orderStatus,
							List<ItemResponse> items,
							String notes,
							BigDecimal totalAmount) {}
