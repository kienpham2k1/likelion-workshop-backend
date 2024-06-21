package org.example.likelion.service;

import org.example.likelion.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    List<Order> gets();

    Page<Order> gets(Pageable pageable);

    Order get(String id);

    void create(Order order);

    void update(String id, Order order);

    void delete(String id);
}
