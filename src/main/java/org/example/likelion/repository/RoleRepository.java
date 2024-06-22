package org.example.likelion.repository;

import org.example.likelion.model.Admin;
import org.example.likelion.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
