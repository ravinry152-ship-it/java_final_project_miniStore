package com.ecommerce.webapi.dto.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class OrderRequest {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("total_amount")
    private double totalAmount;
    @JsonProperty("status")
    private String status;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
