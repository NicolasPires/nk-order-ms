package br.com.nksolucoes.nkorderms.service;

import br.com.nksolucoes.nkorderms.domain.model.Customer;
import br.com.nksolucoes.nkorderms.domain.records.request.CustomerRequest;
import br.com.nksolucoes.nkorderms.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;

	@Transactional
	public Customer createOrUpdateCustomer(CustomerRequest customer) {
		log.info("Starting createOrUpdateCustomer for document: {}", customer.document());

		return customerRepository.findByDocumentAndNameAndAddressAndPhoneAndEmail(
						customer.document(),
						customer.name(),
						customer.address(),
						customer.phoneNumber(),
						customer.email()
				).map(existingCustomer -> updateCustomer(existingCustomer, customer))
				.orElseGet(() -> {
					log.info("Customer not found. Creating a new one for document: {}", customer.document());
					return customerRepository.save(newCustomer(customer));
				});
	}

	private Customer updateCustomer(Customer existingCustomer, CustomerRequest newCustomerData) {
		log.info("Updating customer with ID: {}", existingCustomer.getCustomerId());
		existingCustomer.setDocument(newCustomerData.document());
		existingCustomer.setName(newCustomerData.name());
		existingCustomer.setAddress(newCustomerData.address());
		existingCustomer.setPhone(newCustomerData.phoneNumber());
		existingCustomer.setEmail(newCustomerData.email());
		return customerRepository.save(existingCustomer);
	}

	private Customer newCustomer(CustomerRequest customer) {
		log.info("Creating new customer with document: {}", customer.document());
		return Customer.builder()
				.document(customer.document())
				.name(customer.name())
				.address(customer.address())
				.phone(customer.phoneNumber())
				.email(customer.email())
				.build();
	}
}
