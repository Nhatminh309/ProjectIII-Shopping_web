package org.demo.project.service;

import jakarta.servlet.http.HttpServletRequest;
import org.demo.project.config.VNPayConfig;
import org.demo.project.payment.PaymentStrategy;
import org.demo.project.payment.VNPayStrategy;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PaymentService {
    private PaymentStrategy paymentStrategy;


    // Set phương thức thanh toán
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    // Thực hiện tạo đơn hàng
    public String executeOrder(int amount, String orderInfo, String urlReturn){
        return paymentStrategy.createOrder(amount, orderInfo, urlReturn);
    }

    // Thực hiện chuyển về màn hình giao dịch
    public int excuteReturn(HttpServletRequest request){
        return paymentStrategy.orderReturn(request);
    }
}

