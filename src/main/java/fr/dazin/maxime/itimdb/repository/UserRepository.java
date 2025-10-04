package fr.dazin.maxime.itimdb.repository;

import fr.dazin.maxime.itimdb.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
