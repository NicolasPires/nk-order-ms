package br.com.nksolucoes.nkorderms.service;

import br.com.nksolucoes.nkorderms.domain.model.Customer;
import br.com.nksolucoes.nkorderms.domain.records.request.CustomerRequest;
import br.com.nksolucoes.nkorderms.mocks.CustomerMock;
import br.com.nksolucoes.nkorderms.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private CustomerService customerService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createOrUpdateCustomer_ShouldCreateNewCustomer_WhenCustomerDoesNotExist() {
		CustomerRequest request = CustomerMock.createCustomerRequestMock();

		when(customerRepository.findByDocumentAndNameAndAddressAndPhoneAndEmail(
				eq(request.document()),
				eq(request.name()),
				eq(request.address()),
				eq(request.phoneNumber()),
				eq(request.email())
		)).thenReturn(Optional.empty());

		when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Customer result = customerService.createOrUpdateCustomer(request);

		assertNotNull(result);
		assertEquals(request.document(), result.getDocument());
		assertEquals(request.name(), result.getName());
		assertEquals(request.address(), result.getAddress());
		assertEquals(request.phoneNumber(), result.getPhone());
		assertEquals(request.email(), result.getEmail());
		verify(customerRepository, times(1)).save(any(Customer.class));
	}

	@Test
	void createOrUpdateCustomer_ShouldUpdateExistingCustomer_WhenCustomerExists() {
		Customer existingCustomer = CustomerMock.createCustomerMock();


		CustomerRequest request = CustomerMock.createCustomerRequestMock();

		when(customerRepository.findByDocumentAndNameAndAddressAndPhoneAndEmail(
				eq(request.document()),
				eq(request.name()),
				eq(request.address()),
				eq(request.phoneNumber()),
				eq(request.email())
		)).thenReturn(Optional.of(existingCustomer));

		when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

		// Act
		Customer result = customerService.createOrUpdateCustomer(request);

		// Assert
		assertNotNull(result);
		assertEquals(request.document(), result.getDocument());
		assertEquals(request.name(), result.getName());
		assertEquals(request.address(), result.getAddress());
		assertEquals(request.phoneNumber(), result.getPhone());
		assertEquals(request.email(), result.getEmail());
		assertEquals(existingCustomer.getCustomerId(), result.getCustomerId());
		verify(customerRepository, times(1)).save(existingCustomer);
	}

	@Test
	void createOrUpdateCustomer_ShouldLogMessages_WhenCalled() {
		// Arrange
		CustomerRequest request = CustomerMock.createCustomerRequestMock();

		when(customerRepository.findByDocumentAndNameAndAddressAndPhoneAndEmail(
				eq(request.document()),
				eq(request.name()),
				eq(request.address()),
				eq(request.phoneNumber()),
				eq(request.email())
		)).thenReturn(Optional.empty());

		when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

		// Act
		Customer result = customerService.createOrUpdateCustomer(request);

		// Assert
		assertNotNull(result);
		verify(customerRepository, times(1)).save(any(Customer.class));
	}
}
