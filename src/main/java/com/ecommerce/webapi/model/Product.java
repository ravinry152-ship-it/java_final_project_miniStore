package com.ecommerce.webapi.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreName storeName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String productName;

    private double price;

    // រក្សាទុក path របស់ image
    private String productImage;

    private int stock;

    private LocalDateTime createdAt;
}
