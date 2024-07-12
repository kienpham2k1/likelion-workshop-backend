package org.example.likelion.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.response.UrlResponse;
import org.example.likelion.dto.response.VnPayResponse;
import org.example.likelion.repository.OrderRepository;
import org.example.likelion.service.OrderService;
import org.example.likelion.service.payment.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
@Tag(name = "Payment Service")
public class PaymentController {
    private final PaymentService paymentService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @PostMapping("/submitOrder")
    public UrlResponse submitOrder(
            @RequestParam("orderId") String orderId,
            HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        int orderTotal = (int) orderService.get(orderId).getTotal() * 25000;
        String vnpayUrl = paymentService.createOrder(orderTotal, orderId, baseUrl);
        return new UrlResponse(vnpayUrl);
    }

    @GetMapping("/vnpay-payment")
    public VnPayResponse GetMapping(HttpServletRequest request) {
        int paymentStatus = paymentService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
//        if (paymentStatus == 1) {
//            Order order = orderService.get(orderInfo);
//            order.setPaid(true);
//            orderRepository.save(order);
//        }

        VnPayResponse vnPayResponse = new VnPayResponse(paymentStatus == 1, orderInfo, totalPrice, paymentTime, transactionId);
        return vnPayResponse;
    }
}
