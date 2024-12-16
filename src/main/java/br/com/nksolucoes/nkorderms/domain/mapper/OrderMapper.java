package br.com.nksolucoes.nkorderms.domain.mapper;

import br.com.nksolucoes.nkorderms.domain.model.Order;
import br.com.nksolucoes.nkorderms.domain.records.request.OrderRequest;
import br.com.nksolucoes.nkorderms.domain.records.response.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ItemMapper.class})
public interface OrderMapper {
	Order requestToEntity(OrderRequest request);
	OrderResponse entityToResponse(Order order);
}
