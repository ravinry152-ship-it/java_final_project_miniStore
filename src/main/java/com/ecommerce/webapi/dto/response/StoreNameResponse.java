package com.ecommerce.webapi.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreNameResponse {
    private Long id;
    private String userName;
    private String storeName;
}
