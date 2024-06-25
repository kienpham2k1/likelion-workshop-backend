package org.example.likelion.repository;

import org.example.likelion.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    @Query("select c from Category c where :name is null or upper(cast(c.name as string)) like upper(concat('%', cast(:name as string), '%'))")
    Page<Category> findByNameIsContainingIgnoreCase(@Param("name") String name, Pageable pageable);
}
