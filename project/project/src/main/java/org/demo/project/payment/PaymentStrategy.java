package org.demo.project.payment;

import jakarta.servlet.http.HttpServletRequest;
import org.demo.project.dto.request.PaymentInfo;

public interface PaymentStrategy {
    String createOrder(int total, String orderInfor, String urlReturn);
    int orderReturn(HttpServletRequest request);
}
