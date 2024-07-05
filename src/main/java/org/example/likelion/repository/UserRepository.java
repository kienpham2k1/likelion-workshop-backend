package org.example.likelion.repository;

import org.example.likelion.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    User findUserByUsername(String username);

}
