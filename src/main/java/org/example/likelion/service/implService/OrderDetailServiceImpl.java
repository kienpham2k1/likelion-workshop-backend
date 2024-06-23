package org.example.likelion.service.implService;

import lombok.RequiredArgsConstructor;
import org.example.likelion.constant.ErrorMessage;
import org.example.likelion.exception.EntityNotFoundException;
import org.example.likelion.model.OrderDetail;
import org.example.likelion.repository.OrderDetailRepository;
import org.example.likelion.service.OrderDetailService;
import org.example.likelion.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final ProductService productService;

    @Override
    public List<OrderDetail> gets(String orderId) {
        return orderDetailRepository.findAllByOrderId(orderId);
    }

    @Override
    public Page<OrderDetail> gets(String orderId, Pageable pageable) {
        return orderDetailRepository.findAllByOrderId(orderId, pageable);
    }

    @Override
    public OrderDetail get(String id) {
        return orderDetailRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ORDER_DETAIL_NOT_FOUND));
    }

    @Override
    public OrderDetail create(OrderDetail orderDetail) {
      return   orderDetailRepository.save(orderDetail);
    }
}
