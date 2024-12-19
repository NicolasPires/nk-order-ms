package br.com.nksolucoes.nkorderms.domain.records.request;

import java.util.List;

public record OrderRequest(CustomerRequest customer,
						   String notes,
						   List<ItemRequest> items) {}
