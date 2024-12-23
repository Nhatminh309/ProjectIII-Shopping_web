package org.demo.project.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "user_id")
    private String user_id;

    @Column(name = "order_date")
    private LocalDate order_date;

    @Column(name = "status")
    private String status;

    @Column(name = "total_amount")
    private Integer total_amount;

    @Column(name = "name_customer")
    private String name_customer;

    @Column(name = "shipping_address")
    private String shipping_address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "payment_method")
    private String payment_method;

}
