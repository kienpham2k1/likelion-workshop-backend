package org.example.likelion.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.response.UrlResponse;
import org.example.likelion.repository.OrderRepository;
import org.example.likelion.service.OrderService;
import org.example.likelion.service.payment.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
    public void GetMapping(HttpServletRequest request, HttpServletResponse response) {
        int paymentStatus = paymentService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        try {
            response.sendRedirect("http://localhost:5173/order/status" +
                    "?status=" + (paymentStatus == 1) +
                    "&vnp_OrderInfo=" + orderInfo +
                    "&vnp_PayDate=" + paymentTime +
                    "&vnp_TransactionNo=" + transactionId +
                    "&vnp_Amount=" + totalPrice);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
