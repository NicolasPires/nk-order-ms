package br.com.nksolucoes.nkorderms.service;

import br.com.nksolucoes.nkorderms.domain.mapper.ItemMapper;
import br.com.nksolucoes.nkorderms.domain.model.Item;
import br.com.nksolucoes.nkorderms.domain.records.request.ItemRequest;
import br.com.nksolucoes.nkorderms.exceptions.ItemValidationException;
import br.com.nksolucoes.nkorderms.repository.ItemRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

	private final ItemRepository itemRepository;
	private final ItemMapper itemMapper;

	public ItemService(ItemRepository itemRepository, ItemMapper itemMapper) {
		this.itemRepository = itemRepository;
		this.itemMapper = itemMapper;
	}

	public BigDecimal calculateSubTotal(Item item) {
		validateItem(item);
		return item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
	}

	public Item calculateAndSaveSubTotal(Item item) {
		validateItem(item);
		BigDecimal subtotal = calculateSubTotal(item);
		item.setSubtotal(subtotal);
		return itemRepository.save(item);
	}

	private void validateItem(Item item) {
		if (item == null) {
			throw new ItemValidationException("Item cannot be null");
		}
		if (item.getUnitPrice() == null || item.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) {
			throw new ItemValidationException("Unit price must be non-negative");
		}
		if (item.getQuantity() == null || item.getQuantity() <= 0) {
			throw new ItemValidationException("Quantity must be greater than zero");
		}
	}


	public Item convertRequestToItem(ItemRequest item) {
		return itemMapper.requestToEntity(item);
	}
}
