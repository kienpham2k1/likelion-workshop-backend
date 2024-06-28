package org.example.likelion.repository;

import org.example.likelion.model.Admin;
import org.example.likelion.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    @Query("select u from User u where u.username = ?1")
    Optional<UserDetails> findUserDetailsByUsername(String username);

    boolean existsByUsername(String username);
}
