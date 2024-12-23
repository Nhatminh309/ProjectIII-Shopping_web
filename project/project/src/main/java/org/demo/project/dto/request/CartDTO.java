package org.demo.project.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {
    private String id;
    private String name;
    private String img;
    private Integer quantity;
    private String size;
    private Integer price;

    public CartDTO(String id, String name, String img, Integer quantity, String size, Integer price) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.quantity = quantity;
        this.size = size;
        this.price = price;
    }
}
