package org.example.likelion.service.implService;

import lombok.RequiredArgsConstructor;
import org.example.likelion.constant.ErrorMessage;
import org.example.likelion.dto.auth.Role;
import org.example.likelion.dto.auth.UserDetailsImpl;
import org.example.likelion.enums.OrderStatus;
import org.example.likelion.exception.EntityNotFoundException;
import org.example.likelion.exception.OutOfStockProductException;
import org.example.likelion.model.Order;
import org.example.likelion.repository.OrderRepository;
import org.example.likelion.repository.VoucherRepository;
import org.example.likelion.service.OrderDetailService;
import org.example.likelion.service.OrderService;
import org.example.likelion.service.ProductService;
import org.example.likelion.service.VoucherService;
import org.example.likelion.service.auth.AuthenticationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.example.likelion.utils.ResourceUtils.isValidResourceForUser;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final OrderDetailService orderDetailService;
    private final AuthenticationService authenticationService;
    private final VoucherService voucherService;
    private final VoucherRepository voucherRepository;

    @Override
    public List<Order> gets() {
        return orderRepository.findAll();
    }

    @Override
    public Page<Order> gets(Pageable pageable) {
        String userId = null;
        var curUserLogin = authenticationService.getCurrentUser();
        if (curUserLogin.isPresent())
            if (Objects.equals(curUserLogin.get().getRole(), Role.USER)) userId = curUserLogin.get().getId();
        return orderRepository.findAllByUserId(userId, pageable);
    }

    @Override
    public Order get(String id) {
        var order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ORDER_NOT_FOUND));
        if (!isValidResourceForUser(order.getUserId()))
            throw new AccessDeniedException(ErrorMessage.USER_ACCESS_DENIED);
        return order;
    }

    @Override
    public Order create(Order order) {
        UserDetailsImpl userDetails = authenticationService.getCurrentUser().orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
        order.setCreateDate(LocalDate.now());
        order.getOrderDetails().forEach(e -> {
            if (!productService.isStocking(e.getProductId(), e.getQuantity()))
                throw new OutOfStockProductException(ErrorMessage.OUT_OF_STOCK_PRODUCT);
        });
        order.setUserId(userDetails.getId());

        if (order.getVoucherId() != null) {
            var voucher = voucherRepository.findVoucherByIdAndActiveIsTrueAndQuantityGreaterThan(order.getVoucherId());
            if (voucher == null) {
                throw new OutOfStockProductException(ErrorMessage.OUT_OF_STOCK_VOUCHER);
            }
            voucherService.reduce(order.getVoucherId());
        }
        
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
        if (!isValidResourceForUser(order.getUserId()))
            throw new AccessDeniedException(ErrorMessage.USER_ACCESS_DENIED);
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }

    @Override
    public void cancel(String id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ORDER_NOT_FOUND));
        if (!isValidResourceForUser(order.getUserId()))
            throw new AccessDeniedException(ErrorMessage.USER_ACCESS_DENIED);
        order.setCancel(true);
        orderRepository.save(order);
    }
}
