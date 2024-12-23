package org.demo.project.controller;

import jakarta.servlet.http.HttpSession;
import org.demo.project.dto.request.OrderItemDTO;
import org.demo.project.entity.Order;
import org.demo.project.entity.Order_Item;
import org.demo.project.entity.Product;
import org.demo.project.service.ManagerService;
import org.demo.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ManagerService managerService;

    // Xem các đơn hàng đang chờ
    @GetMapping("/manager/viewAllOrders")
    public String viewAllOrders(Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!("manager".equals(role))){
            return "logIn";
        }
        // Đặt trạng thái đơn hàng thành đang chờ
        List<Order> orders = orderService.getOrders("Pending");
        model.addAttribute("orders", orders);
        return "manager/viewAllOrders";
    }

    @GetMapping("/manager/viewOrder")
    public String viewOrder(@RequestParam("orderId") String orderId, Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!("manager".equals(role))){
            return "logIn";
        }
        List<Order_Item> orderItems = orderService.getOrder_item(orderId);
        List<OrderItemDTO> items = new ArrayList<>();
        for (Order_Item orderItem : orderItems) {
            Product product = managerService.getProduct(orderItem.getProduct_id());

            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setProductId(orderItem.getProduct_id());
            orderItemDTO.setProductName(product.getProduct_name());
            orderItemDTO.setProductImg(product.getMainImage());
            orderItemDTO.setQuantity(orderItem.getQuantity());
            orderItemDTO.setSize(orderItem.getSize());
            orderItemDTO.setPrice(orderItem.getPrize());
            items.add(orderItemDTO);
        }
        model.addAttribute("items", items);
        return "manager/viewOrder";
    }

    @PostMapping("/manager/updateShipping")
    public String updateShipping(@RequestParam("orderId") String orderId, Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!("manager".equals(role))){
            return "logIn";
        }
        orderService.updateShipping(orderId, "Delivering");
        return "redirect:/manager/viewAllOrders";
    }


    // Xem các đơn hàng đang giao
    @GetMapping("/manager/viewShipping")
    public String viewShipping(Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!("manager".equals(role))){
            return "logIn";
        }
        List<Order> orders = orderService.getOrders("Delivering");
        model.addAttribute("orders", orders);
        return "manager/viewShipping";
    }

    @PostMapping("/manager/updateSuccessful")
    public String updateSuccessful(@RequestParam("orderId") String orderId, Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!("manager".equals(role))){
            return "logIn";
        }
        orderService.updateShipping(orderId, "Successful");
        return "redirect:/manager/viewShipping";
    }

    // Xem các đơn hàng thành công
    @GetMapping("/manager/viewSuccessful")
    public String viewSuccessful(Model model, HttpSession session ) {
        String role = (String) session.getAttribute("role");
        if (!("manager".equals(role))){
            return "logIn";
        }
        List<Order> orders = orderService.getOrders("Successful");
        model.addAttribute("orders", orders);
        return "manager/viewSuccessful";
    }

}


