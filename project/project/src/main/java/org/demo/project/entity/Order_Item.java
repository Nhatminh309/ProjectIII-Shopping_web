package org.demo.project.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Setter
@Getter
@Table(name = "order_item")
public class Order_Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "order_id")
    private String order_id;

    @Column(name = "product_id")
    private String product_id;

    @Column(name = "product_img")
    private String product_img;

    @Column(name = "size")
    private String size;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name ="prize")
    private Integer prize;

    @Column(name = "total")
    private Integer total;


    public Order_Item() {
        
    }
}
