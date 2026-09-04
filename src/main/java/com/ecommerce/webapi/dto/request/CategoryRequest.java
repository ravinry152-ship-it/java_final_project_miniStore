package com.ecommerce.webapi.dto.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString

public class CategoryRequest {
    @JsonProperty("category_name")
    private  String categoryName;
    @JsonProperty("product_image")
    private String productImage;
}
