package org.example.likelion.service.implService;

import lombok.RequiredArgsConstructor;
import org.example.likelion.constant.ErrorMessage;
import org.example.likelion.exception.EntityNotFoundException;
import org.example.likelion.model.Order;
import org.example.likelion.repository.OrderRepository;
import org.example.likelion.service.IOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;

    @Override
    public List<Order> gets() {
        return orderRepository.findAll();
    }

    @Override
    public Page<Order> gets(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order get(String id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ORDER_NOT_FOUND));
    }

    @Override
    public void create(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void update(String id, Order order) {
        orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ORDER_NOT_FOUND));
        orderRepository.save(order);
    }

    @Override
    public void delete(String id) {
        orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ORDER_NOT_FOUND));
        orderRepository.deleteById(id);
    }
}
