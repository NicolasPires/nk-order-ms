package br.com.nksolucoes.nkorderms.mocks;

import br.com.nksolucoes.nkorderms.domain.model.Customer;
import br.com.nksolucoes.nkorderms.domain.records.request.CustomerRequest;
import br.com.nksolucoes.nkorderms.domain.records.response.CustomerResponse;

public class CustomerMock {

	public static Customer createCustomerMock() {
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setDocument("12345678900");
		customer.setName("John Doe");
		customer.setEmail("johndoe@example.com");
		customer.setPhone("123456789");
		customer.setAddress("123 Mock Street");
		return customer;
	}

	public static Customer createCustomerWithSpecificValues(Long id, String name, String document) {
		Customer customer = new Customer();
		customer.setCustomerId(id);
		customer.setName(name);
		customer.setDocument(document);
		customer.setEmail("customemail@example.com");
		customer.setPhone("987654321");
		customer.setAddress("Specific Address 456");
		return customer;
	}

	public static CustomerRequest createCustomerRequestMock() {
		return new CustomerRequest(
				"12345678900",
				"John Doe",
				"123 Street Name",
				"123456789",
				"john.doe@example.com"
		);
	}
}
