package org.example.likelion.repository;

import org.example.likelion.dto.auth.Role;
import org.example.likelion.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("select new Product ( p.name, p.description, p.price, cast(sum(p.quantity) as int), (select p2.imgLink from Product p2 where p2.name = p.name order by p2.id limit 1) )" +
            "from Product p " +
            "where :name is null or upper(CAST(p.name as string)) like upper(CAST(concat('%', :name, '%')as string)) " +
            "group by p.name, p.description, p.price")
    Page<Product> findByNameContainsIgnoreCase(@Param("name") String name, Pageable pageable);

    List<Product> findAllByName(String productName);
}
