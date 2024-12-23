package org.demo.project.entity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Data
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private String id;

    @Column(name = "product_name")
    private String product_name;
    @Column(name = "description")
    private String description;
    @Column(name = "type")
    private String type;
    @Column(name = "prize")
    private Integer prize;
    @Column(name = "created_at")
    private LocalDate created_at;
    @Column(name = "main_images")
    private String mainImage;
    @Column(name = "bought")
    private Integer bought = 0;
    @Column(name = "is_deleted")
    private Boolean is_deleted = false;

}
