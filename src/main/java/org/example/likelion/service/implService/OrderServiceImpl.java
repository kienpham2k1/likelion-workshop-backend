package org.example.likelion.service.implService;

import lombok.RequiredArgsConstructor;
import org.example.likelion.constant.ErrorMessage;
import org.example.likelion.dto.auth.UserDetailsImpl;
import org.example.likelion.enums.OrderStatus;
import org.example.likelion.exception.EntityNotFoundException;
import org.example.likelion.exception.OutOfStockProductException;
import org.example.likelion.model.Order;
import org.example.likelion.repository.OrderRepository;
import org.example.likelion.service.OrderDetailService;
import org.example.likelion.service.OrderService;
import org.example.likelion.service.ProductService;
import org.example.likelion.service.auth.AuthenticationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final OrderDetailService orderDetailService;
    private final AuthenticationService authenticationService;
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
    public Order create(Order order) {
        UserDetailsImpl userDetails = authenticationService.getCurrentUser().orElseThrow(()-> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
        order.setCreateDate(LocalDate.now());
        order.getOrderDetails().forEach(e -> {
            if (!productService.isStocking(e.getProductId(), e.getQuantity()))
                throw new OutOfStockProductException(ErrorMessage.OUT_OF_STOCK_PRODUCT);
        });
        order.setUserId(userDetails.getId());
        Order o = orderRepository.save(order);
        order.getOrderDetails().forEach(e -> {
            e.setOrderId(o.getId());
            productService.reduce(e.getProductId(), e.getQuantity());
            orderDetailService.create(e);
        });
        return o;
    }

    @Override
    public Order update(String id, Order order) {
        orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ORDER_NOT_FOUND));
        return orderRepository.save(order);
    }

    @Override
    public Order updateStatus(String id, OrderStatus status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ORDER_NOT_FOUND));
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }

    @Override
    public void cancel(String id) {
        Order cur = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ORDER_NOT_FOUND));
        cur.setCancel(true);
        orderRepository.save(cur);
    }
}
