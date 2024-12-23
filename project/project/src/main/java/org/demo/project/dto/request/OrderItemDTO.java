package org.demo.project.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
    private String productId;
    private String productName;
    private String productImg;
    private String size;
    private int quantity;
    private int price;
}
