package nl.novi.autogarage_roy_kersten.repository;

import nl.novi.autogarage_roy_kersten.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
