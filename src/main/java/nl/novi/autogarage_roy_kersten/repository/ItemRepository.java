package nl.novi.autogarage_roy_kersten.repository;

import nl.novi.autogarage_roy_kersten.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findById(long idItem);


}
