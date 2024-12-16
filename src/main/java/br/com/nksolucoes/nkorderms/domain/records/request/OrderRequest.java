package br.com.nksolucoes.nkorderms.domain.records.request;

import java.util.List;

public record OrderRequest(Long orderId,
						   String customerName,
						   String notes,
						   List<ItemRequest> items) {}
