package org.example.likelion.repository;

import jakarta.persistence.Tuple;
import org.example.likelion.dto.auth.UserDetailsImpl;
import org.example.likelion.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends  JpaRepository<User, String> {
    @Query(name = "find_account_by_name", nativeQuery = true)
    List<UserDetailsImpl> findAccountByUsername(
            @Param("username") String username);
    @Query(value = """
            select user_id, username, password, "role"
            from (
                select u.user_id, u.username, u."password", u."role" from "user" u
                union all
                select a.admin_id, a.username, a."password", a."role" from "admin" a
            ) as UserDetails
            where username = :username;
            """, nativeQuery = true)
    Optional<Tuple> findUserDetailsByUsername(@Param("username") String username);
}