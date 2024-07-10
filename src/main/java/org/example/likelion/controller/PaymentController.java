package org.example.likelion.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.response.VnPayResponse;
import org.example.likelion.service.payment.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
@Tag(name = "Payment Service")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/submitOrder")
    public String submitOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderId") String orderId,
                              HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = paymentService.createOrder(orderTotal, orderId, baseUrl);
        return vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public VnPayResponse GetMapping(HttpServletRequest request) {
        int paymentStatus = paymentService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        VnPayResponse vnPayResponse = new VnPayResponse(paymentStatus == 1, orderInfo, totalPrice, paymentTime, transactionId);
        return vnPayResponse;
    }
}
