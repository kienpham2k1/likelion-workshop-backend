package org.example.likelion.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.response.UrlResponse;
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
    public void getMapping(HttpServletRequest request, HttpServletResponse response) {
        paymentService.getMapping(request, response);
    }
}
