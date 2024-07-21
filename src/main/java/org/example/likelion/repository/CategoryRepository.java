package org.example.likelion.repository;

import org.example.likelion.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    @Override
    @Query("select c from Category c JOIN FETCH c.promotions p  where c.id = :categoryId and p.expiredDate > current_date ")
    Optional<Category> findById(@Param("categoryId") String id);
}
