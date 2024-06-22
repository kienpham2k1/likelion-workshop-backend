package org.example.likelion.service;

import org.example.likelion.model.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderDetailService {
    List<OrderDetail> gets(String orderId);

    Page<OrderDetail> gets(String orderId, Pageable pageable);

    OrderDetail get(String id);

    OrderDetail create(OrderDetail orderDetail);

    OrderDetail update(String id, OrderDetail orderDetail);

    void delete(String id);
}
