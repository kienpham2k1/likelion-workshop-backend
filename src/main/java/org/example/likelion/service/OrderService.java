package org.example.likelion.service;

import jakarta.servlet.http.HttpServletResponse;
import org.example.likelion.enums.OrderStatus;
import org.example.likelion.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    List<Order> gets();

    Page<Order> gets(Pageable pageable);

    Order get(String id);

    Order create(Order order);

    Order update(String id, Order order);

    Order updateStatus(String id, OrderStatus status);

    Order updatePayment(String id);

    void cancel(String id);

    void exportMonthlyReport(HttpServletResponse response);
}
