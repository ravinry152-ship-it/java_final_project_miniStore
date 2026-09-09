package com.ecommerce.webapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ProductResponse {
    private Long id;
    @JsonProperty("store_id")
    private Long storeId;
    @JsonProperty("category_id")
    private Long categoryId;
    @JsonProperty("product_name")
    private String productName;
    private double price;
    @JsonProperty("product_image")
    private String productImage;
    private int stock;
}
