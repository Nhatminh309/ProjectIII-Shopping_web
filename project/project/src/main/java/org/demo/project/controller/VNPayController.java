package org.demo.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;import org.demo.project.dto.request.OrderRequest;
import org.demo.project.entity.Order;
import org.demo.project.payment.VNPayStrategy;
import org.demo.project.service.OrderService;
import org.demo.project.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Controller
public class VNPayController {
    @Autowired
    private OrderService orderService;


    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, HttpSession session, Model model){
        VNPayStrategy strategy = new VNPayStrategy();
        PaymentService paymentService = new PaymentService();
        paymentService.setPaymentStrategy(strategy);
        int paymentStatus = paymentService.excuteReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");


        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        if (paymentStatus == 1){ // Thanh toán thành công
            orderService.saveOrder("VN Pay", session );
        }
        return paymentStatus == 1 ? "pay/ordersuccess" : "pay/orderfail";
    }
}
