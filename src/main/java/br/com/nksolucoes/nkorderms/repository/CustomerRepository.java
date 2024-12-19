package br.com.nksolucoes.nkorderms.repository;

import br.com.nksolucoes.nkorderms.domain.model.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByDocumentAndNameAndAddressAndPhoneAndEmail(String document,
																	   String name,
																	   String address,
																	   String phone,
																	   String email);
	}
