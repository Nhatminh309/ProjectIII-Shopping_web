package org.demo.project.service;

import jakarta.servlet.http.HttpSession;
import org.demo.project.dto.request.OrderRequest;
import org.demo.project.entity.Cart_item;
import org.demo.project.entity.Order;
import org.demo.project.entity.Order_Item;
import org.demo.project.repository.Cart_ItemRepository;
import org.demo.project.repository.OrderRepository;
import org.demo.project.repository.Order_ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    Order_ItemRepository orderItemRepository;
    @Autowired
    private Cart_ItemRepository cart_ItemRepository;

    public List<Order> getOrders(String status) {
        return orderRepository.findAllOrder(status);
    }

    public List<Order_Item> getOrder_item(String order_id) {
        return orderItemRepository.findByOrder_id(order_id);
    }

    @Transactional
    public void updateShipping(String order_id, String status){
        orderRepository.updateStatus(order_id, status);
    }



    // Lưu order vào db
    public void saveOrder(String paymentMethod, HttpSession session){
        String user_id = (String) session.getAttribute("userId");
        LocalDate order_date = LocalDate.now();
        String status = "Pending";
        int total_amount = (int) session.getAttribute("TotalPrice");
        String shipping_address = (String) session.getAttribute("address");
        String phone = (String) session.getAttribute("phone");
        String name_customer = (String) session.getAttribute("fullName");

        Order order = new Order();
        order.setUser_id(user_id);
        order.setOrder_date(order_date);
        order.setStatus(status);
        order.setTotal_amount(total_amount);
        order.setShipping_address(shipping_address);
        order.setPayment_method(paymentMethod);
        order.setPhone(phone);
        order.setName_customer(name_customer);


        String orderId = orderRepository.save(order).getId();

        // Lưu các order_items
        System.out.println("aaaaaa");
        List<OrderRequest.CartItem> cartItems = (List<OrderRequest.CartItem>) session.getAttribute("CartItems");
        System.out.println(cartItems);

        if (cartItems != null) {
            for (OrderRequest.CartItem cartItem : cartItems) {
                // Lấy ra cartItem
                Cart_item cart_item = cart_ItemRepository.findById(cartItem.getId()).orElse(null);
                if (cart_item != null) {
                    // Lưu thông tin vào orderItem
                    Order_Item order_item = new Order_Item();
                    order_item.setOrder_id(orderId);
                    order_item.setProduct_id(cart_item.getProductId());
                    order_item.setQuantity(cartItem.getQuantity());
                    order_item.setPrize(cart_item.getPrize());
                    order_item.setSize(cart_item.getSize());
                    order_item.setTotal(cart_item.getPrize() * cartItem.getQuantity());
                    orderItemRepository.save(order_item);

                    // Xóa thông tin trong cartItem
                    cart_ItemRepository.delete(cart_item);
                }
            }
        } else {
            System.out.println("CartItems is null or empty.");
        }




    }

}
