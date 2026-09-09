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
public class CategoryResponse {
    private Long id;
    @JsonProperty("category_name")
    private  String categoryName;
    @JsonProperty("product_image")
    private String productImage;
}
