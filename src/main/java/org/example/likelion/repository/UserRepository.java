package org.example.likelion.repository;

import org.example.likelion.model.Admin;
import org.example.likelion.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
