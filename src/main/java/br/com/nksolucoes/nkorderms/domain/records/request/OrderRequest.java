package br.com.nksolucoes.nkorderms.domain.records.request;

import java.util.List;

public record OrderRequest(String customerName,
						   String notes,
						   List<ItemRequest> items) {}
