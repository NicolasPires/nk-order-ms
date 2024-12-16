package br.com.nksolucoes.nkorderms.service;

import br.com.nksolucoes.nkorderms.domain.model.Item;
import br.com.nksolucoes.nkorderms.repository.ItemRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	public BigDecimal calculateSubTotalOrderItem(Item item) {
		var subtotalCalculated = BigDecimal.valueOf(item.getUnitPrice().doubleValue())
				.multiply(BigDecimal.valueOf(item.getQuantity()));

		item.setSubtotal(subtotalCalculated);
		itemRepository.save(item);

		return subtotalCalculated;
	}
}
