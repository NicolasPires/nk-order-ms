package br.com.nksolucoes.nkorderms.domain.mapper;

import br.com.nksolucoes.nkorderms.domain.model.Item;
import br.com.nksolucoes.nkorderms.domain.records.request.ItemRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {

	@Mapping(target = "itemId", ignore = true)
	@Mapping(target = "subtotal", ignore = true)
	Item toEntity(ItemRequest request);
}
