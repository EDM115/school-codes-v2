package fr.iutvannes.info.but3.userservice.repository;

import fr.iutvannes.info.but3.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
