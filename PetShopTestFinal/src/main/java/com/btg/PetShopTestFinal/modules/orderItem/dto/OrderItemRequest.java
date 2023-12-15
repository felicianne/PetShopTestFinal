package com.btg.PetShopTestFinal.modules.orderItem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderItemRequest {
    @NotBlank()
    private String productId;
    @NotBlank()
    private Integer amount;
}
