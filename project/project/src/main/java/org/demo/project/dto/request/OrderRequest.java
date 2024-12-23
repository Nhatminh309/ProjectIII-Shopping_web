package org.demo.project.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private List<CartItem> cartItems;
    private String shippingMethod;
    private String paymentMethod;
    private String promoCode;
    private int totalPrice;

    // Getter và Setter

    public static class CartItem {
        private String id;
        private int quantity;
        private double price;
        private String size;

        public String getId() {
            return id;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getPrice() {
            return price;
        }

        public String getSize() {
            return size;
        }
    }
}

