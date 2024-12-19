package br.com.nksolucoes.nkorderms.domain.mapper;

import br.com.nksolucoes.nkorderms.domain.model.Order;
import br.com.nksolucoes.nkorderms.domain.records.request.OrderRequest;
import br.com.nksolucoes.nkorderms.domain.records.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ItemMapper.class, CustomerMapper.class})
public interface OrderMapper {

//	@Mapping(target = "customer", source = "customer")
//	@Mapping(target = "notes", source = "notes")
//	@Mapping(target = "items", source = "items")
	Order requestToEntity(OrderRequest request);

//	@Mapping(target = "customer", source = "customer")
	OrderResponse entityToResponse(Order order);
}
