package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
abstract class ItemServiceTest {

    @Mock
    ItemRepository itemRepository;

    @Test
    void addItemTest() {}

    @Test
    void getItemById() {}

    @Test
    void deleteItemById() {}



}
