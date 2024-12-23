package org.demo.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.demo.project.dto.request.OrderRequest;
import org.demo.project.payment.VNPayStrategy;
import org.demo.project.service.OrderService;
import org.demo.project.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

@Controller
public class PaymentController {
    @Autowired
    OrderService orderService;

    // Xử lý đơn hàng
    @PostMapping("/user/placeOrder")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest, HttpSession session, Model model) {

        session.setAttribute("CartItems", orderRequest.getCartItems());
        session.setAttribute("ShippingMethod", orderRequest.getShippingMethod());
        session.setAttribute("PaymentMethod", orderRequest.getPaymentMethod());
        session.setAttribute("TotalPrice", orderRequest.getTotalPrice());

        String fullName = (String) session.getAttribute("fullName");
        String phone = (String) session.getAttribute("phone");
        String province = (String) session.getAttribute("province");
        String district = (String) session.getAttribute("district");
        String ward = (String) session.getAttribute("ward");
        String address = (String) session.getAttribute("address");

        if (fullName == null || phone == null || province == null || district == null || ward == null || address == null) {
            return ResponseEntity.badRequest().body("");
        }

        return ResponseEntity.ok("Order placed successfully.");
    }

    @GetMapping("/pay")
    public String pay(HttpSession session, Model model, HttpServletRequest request) {
        String paymentMethod = (String) session.getAttribute("PaymentMethod");
        String user_id = (String) session.getAttribute("userId");
        String order_date = String.valueOf(LocalDate.now());
        int total_amount = (int) session.getAttribute("TotalPrice");

        // Đặt thông tin orderInfo
        String orderInfo = user_id + " " + order_date;
        if ("Thanh toán khi nhận hàng".equals(paymentMethod)) {
            // Lưu thông tin vào database
            orderService.saveOrder("COD", session );

            return "pay/successOrder";
        }
        else if ("VN Pay".equals(paymentMethod)) {
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            VNPayStrategy strategy = new VNPayStrategy();
            PaymentService paymentService = new PaymentService();
            paymentService.setPaymentStrategy(strategy);
            String vnpayUrl = paymentService.executeOrder(total_amount, orderInfo, baseUrl);
            return "redirect:" + vnpayUrl;
        }

        return "pay";
    }
}
