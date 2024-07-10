package org.example.likelion.service.payment;

import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    String createOrder(int total, String orderInfo, String urlReturn);

    int orderReturn(HttpServletRequest request);
}
