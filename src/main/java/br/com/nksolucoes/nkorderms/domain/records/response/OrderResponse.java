package br.com.nksolucoes.nkorderms.domain.records.response;

import br.com.nksolucoes.nkorderms.domain.enums.OrderStatusEnum;
import br.com.nksolucoes.nkorderms.domain.records.request.ItemRequest;
import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(Long orderId,
							String customerName,
							OrderStatusEnum orderStatus,
							List<ItemRequest> items,
							String notes,
							BigDecimal totalAmount) {}
