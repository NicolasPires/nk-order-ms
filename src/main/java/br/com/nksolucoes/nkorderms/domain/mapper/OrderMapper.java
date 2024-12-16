package br.com.nksolucoes.nkorderms.domain.mapper;

import br.com.nksolucoes.nkorderms.domain.model.Order;
import br.com.nksolucoes.nkorderms.domain.records.request.OrderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ItemMapper.class})
public interface OrderMapper {

	@Mapping(target = "orderId", ignore = true)
	@Mapping(target = "totalAmount", ignore = true)
	@Mapping(target = "orderStatus", ignore = true)
	Order toEntity(OrderRequest request);
}
