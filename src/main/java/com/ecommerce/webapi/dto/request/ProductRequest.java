package com.ecommerce.webapi.dto.request;

import com.ecommerce.webapi.model.StoreName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductRequest {
    private StoreName storeName;

    private Long category;

    private String productName;

    private double price;

    @JsonProperty("product_image")

    private String productImage;

   @JsonProperty("stock")
    private int stock;
}
