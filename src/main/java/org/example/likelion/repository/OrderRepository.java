package org.example.likelion.repository;

import org.example.likelion.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("select o from Order o where (:userId is null or o.userId = :userId)")
    Page<Order> findAllByUserId(@Param("userId") String userId, Pageable pageable);
}
