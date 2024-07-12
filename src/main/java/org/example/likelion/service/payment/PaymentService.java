package org.example.likelion.service.payment;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface PaymentService {
    String createOrder(int total, String orderInfo, String urlReturn);

    int orderReturn(HttpServletRequest request);

    void getMapping(HttpServletRequest request, HttpServletResponse response);
}
