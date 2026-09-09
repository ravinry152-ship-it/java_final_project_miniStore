package com.ecommerce.webapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreNameResponse {
    private Long id;

    @JsonProperty("user_name")
    private String user;

    @JsonProperty("store_name")
    private String storeName;

    @JsonProperty("description")
    private String description;
}