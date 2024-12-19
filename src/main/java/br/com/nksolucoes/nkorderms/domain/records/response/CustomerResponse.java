package br.com.nksolucoes.nkorderms.domain.records.response;

public record CustomerResponse(Long customerId,
							   String document,
		 					   String name,
							   String address,
							   String phone,
							   String email) {}
