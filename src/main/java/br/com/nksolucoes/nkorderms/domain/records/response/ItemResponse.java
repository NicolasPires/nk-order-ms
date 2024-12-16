package br.com.nksolucoes.nkorderms.domain.records.response;

import java.math.BigDecimal;

public record ItemResponse(Long itemId,
		                   String description,
						   Integer quantity,
						   BigDecimal unitPrice,
						   BigDecimal subtotal) {}
