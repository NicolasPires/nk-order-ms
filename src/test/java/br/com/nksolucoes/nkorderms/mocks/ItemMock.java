package br.com.nksolucoes.nkorderms.mocks;

import br.com.nksolucoes.nkorderms.domain.model.Item;
import java.math.BigDecimal;

public class ItemMock {

	public static Item createItemMock() {
		Item item = new Item();
		item.setItemId(1L);
		item.setDescription("Item Description");
		item.setQuantity(10);
		item.setSubtotal(BigDecimal.ONE);
		return item;
	}

}
