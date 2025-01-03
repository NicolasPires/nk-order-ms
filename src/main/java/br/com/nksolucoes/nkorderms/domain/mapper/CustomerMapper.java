package br.com.nksolucoes.nkorderms.domain.mapper;

import br.com.nksolucoes.nkorderms.domain.model.Customer;
import br.com.nksolucoes.nkorderms.domain.records.request.CustomerRequest;
import br.com.nksolucoes.nkorderms.domain.records.response.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

//	@Mapping(target = "document", source = "document")
//	@Mapping(target = "name", source = "name")
//	@Mapping(target = "email", source = "email")
//	@Mapping(target = "address", source = "address")
//	@Mapping(target = "phone", source = "phoneNumber")
	Customer requestToEntity(CustomerRequest request);

	CustomerResponse entityToResponse(Customer customer);
}
