package br.com.nksolucoes.nkorderms.domain.mapper;

import br.com.nksolucoes.nkorderms.domain.model.Item;
import br.com.nksolucoes.nkorderms.domain.records.request.ItemRequest;
import br.com.nksolucoes.nkorderms.domain.records.response.ItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {

	@Mapping(target = "itemId", ignore = true)
	@Mapping(target = "subtotal", ignore = true)
	Item requestToEntity(ItemRequest request);

	ItemResponse entityToResponse(Item item);
}
