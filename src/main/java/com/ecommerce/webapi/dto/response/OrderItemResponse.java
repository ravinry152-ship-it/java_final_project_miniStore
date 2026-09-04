package com.ecommerce.webapi.dto.response;
import com.ecommerce.webapi.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderItemResponse {
    private Long id;
    @JsonProperty("order_id")
    private String order;

    @JsonProperty("product_id")
    private Product product;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("price")
    private double price;
}
