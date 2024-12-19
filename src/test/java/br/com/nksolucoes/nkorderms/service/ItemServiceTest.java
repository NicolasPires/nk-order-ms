package br.com.nksolucoes.nkorderms.service;

import br.com.nksolucoes.nkorderms.domain.mapper.ItemMapper;
import br.com.nksolucoes.nkorderms.domain.model.Item;
import br.com.nksolucoes.nkorderms.domain.records.request.ItemRequest;
import br.com.nksolucoes.nkorderms.exceptions.ItemValidationException;
import br.com.nksolucoes.nkorderms.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ItemServiceTest {

	@Mock
	private ItemRepository itemRepository;

	@Mock
	private ItemMapper itemMapper;

	@InjectMocks
	private ItemService itemService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void calculateSubTotal_ShouldReturnCorrectSubtotal_WhenItemIsValid() {
		Item item = new Item();
		item.setUnitPrice(BigDecimal.valueOf(10.00));
		item.setQuantity(2);

		BigDecimal subtotal = itemService.calculateSubTotal(item);

		assertNotNull(subtotal);
		assertEquals(BigDecimal.valueOf(20.00), subtotal);
	}

	@Test
	void calculateSubTotal_ShouldThrowException_WhenItemIsNull() {
		ItemValidationException exception = assertThrows(ItemValidationException.class, () -> itemService.calculateSubTotal(null));
		assertEquals("Item cannot be null", exception.getMessage());
	}

	@Test
	void calculateSubTotal_ShouldThrowException_WhenUnitPriceIsNegative() {
		Item item = new Item();
		item.setUnitPrice(BigDecimal.valueOf(-5.00));
		item.setQuantity(2);

		ItemValidationException exception = assertThrows(ItemValidationException.class, () -> itemService.calculateSubTotal(item));
		assertEquals("Unit price must be non-negative", exception.getMessage());
	}

	@Test
	void calculateSubTotal_ShouldThrowException_WhenQuantityIsZeroOrNegative() {
		Item item = new Item();
		item.setUnitPrice(BigDecimal.valueOf(10.00));
		item.setQuantity(0);

		ItemValidationException exception = assertThrows(ItemValidationException.class, () -> itemService.calculateSubTotal(item));
		assertEquals("Quantity must be greater than zero", exception.getMessage());
	}

	@Test
	void calculateAndSaveSubTotal_ShouldSaveItemWithCorrectSubtotal_WhenItemIsValid() {
		Item item = new Item();
		item.setUnitPrice(BigDecimal.valueOf(10.00));
		item.setQuantity(3);
		when(itemRepository.save(any(Item.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Item savedItem = itemService.calculateAndSaveSubTotal(item);

		assertNotNull(savedItem);
		assertEquals(BigDecimal.valueOf(30.00), savedItem.getSubtotal());
		verify(itemRepository, times(1)).save(item);
	}

	@Test
	void convertRequestToItem_ShouldCallMapperAndReturnItem_WhenRequestIsValid() {
		ItemRequest itemRequest = mock(ItemRequest.class);
		Item item = new Item();
		when(itemMapper.requestToEntity(itemRequest)).thenReturn(item);

		Item result = itemService.convertRequestToItem(itemRequest);

		assertNotNull(result);
		assertEquals(item, result);
		verify(itemMapper, times(1)).requestToEntity(itemRequest);
	}
}
