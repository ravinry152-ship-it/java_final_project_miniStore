package com.ecommerce.webapi.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreNameRequest {
    @JsonProperty("store_name")
    private String storeName;
    @JsonProperty("description")
    private String description;
}
