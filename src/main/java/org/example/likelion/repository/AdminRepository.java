package org.example.likelion.repository;

import org.example.likelion.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    Optional<Admin> findByUsername(String username);

    @Query("select a from Admin a where a.username = ?1")
    Optional<UserDetails> findUserDetailsByUsername(String username);
}
