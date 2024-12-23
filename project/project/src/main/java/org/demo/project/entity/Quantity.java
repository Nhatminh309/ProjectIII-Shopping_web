package org.demo.project.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "Quantity")
public class Quantity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "product_id")
    private String product_id;

    @Column(name = "size")
    private String size;

    @Column(name = "quantity")
    private Integer quantity;
}
