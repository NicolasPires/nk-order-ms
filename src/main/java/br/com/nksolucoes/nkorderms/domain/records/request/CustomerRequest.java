package br.com.nksolucoes.nkorderms.domain.records.request;

public record CustomerRequest(String document,
		                      String name,
							  String address,
							  String phoneNumber,
							  String email) {}
