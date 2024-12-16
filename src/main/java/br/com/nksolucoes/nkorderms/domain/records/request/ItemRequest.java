package br.com.nksolucoes.nkorderms.domain.records.request;

import java.math.BigDecimal;

public record ItemRequest(String description,
						  Integer quantity,
						  BigDecimal unitPrice) {}
