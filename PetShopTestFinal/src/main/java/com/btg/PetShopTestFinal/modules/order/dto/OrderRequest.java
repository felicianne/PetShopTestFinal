package com.btg.PetShopTestFinal.modules.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderRequest {
    @NotBlank()
    private String customerId;
}
