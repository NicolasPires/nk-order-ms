package br.com.nksolucoes.nkorderms.domain.mapper;

import br.com.nksolucoes.nkorderms.domain.model.Item;
import br.com.nksolucoes.nkorderms.domain.records.request.ItemRequest;
import br.com.nksolucoes.nkorderms.domain.records.response.ItemResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {
	Item requestToEntity(ItemRequest request);
	ItemResponse entityToResponse(Item item);
}
