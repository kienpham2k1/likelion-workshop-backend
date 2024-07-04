package org.example.likelion.repository;

import org.example.likelion.dto.auth.Account;
import org.example.likelion.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<User, String> {
    @Query(name = "findAccountByUsername", nativeQuery = true)
    Optional<Account> findAccountByUsername(@Param("username") String username);
    @Query(value = "select u from User u where u.username = :username")
    Optional<UserDetails> findUserDetailsByUsername(@Param("username") String username);
}