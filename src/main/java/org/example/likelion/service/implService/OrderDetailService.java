package org.example.likelion.service.implService;

import lombok.RequiredArgsConstructor;
import org.example.likelion.constant.ErrorMessage;
import org.example.likelion.exception.EntityNotFoundException;
import org.example.likelion.exception.OutOfStockProductException;
import org.example.likelion.model.Category;
import org.example.likelion.model.OrderDetail;
import org.example.likelion.repository.OrderDetailRepository;
import org.example.likelion.repository.ProductRepository;
import org.example.likelion.service.IOrderDetailService;
import org.example.likelion.service.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final IProductService productService;

    @Override
    public List<OrderDetail> gets(String orderId) {
        return orderDetailRepository.findAllByOrderId(orderId);
    }

    @Override
    public Page<OrderDetail> gets(String orderId, Pageable pageable) {
        return  orderDetailRepository.findAllByOrderId(orderId, pageable);
    }

    @Override
    public OrderDetail get(String id) {
        return orderDetailRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ORDER_DETAIL_NOT_FOUND));
    }

    @Override
    public void create(OrderDetail orderDetail) {
        if (productService.isStocking(orderDetail.getProduct().getId(), orderDetail.getQuantity()))
            orderDetailRepository.save(orderDetail);
        else throw new OutOfStockProductException(ErrorMessage.OUT_OF_STOCK_PRODUCT);
    }

    @Override
    public void update(String id, OrderDetail orderDetail) {
        orderDetailRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ORDER_DETAIL_NOT_FOUND));
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public void delete(String id) {
        orderDetailRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ORDER_DETAIL_NOT_FOUND));
        orderDetailRepository.deleteById(id);
    }
}
