package com.btg.PetShopTestFinal.modules.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    @Schema(name = "Product ID", example = "1", required = true)
    private String skuId;

    @Schema(name = "name", example = "shampoo", required = true)
    @NotBlank()
    private String name;

    @Schema(name = "description", example = "shampoo, soap...")
    private String description;

    @Schema(name = "quantity", example = "10", required = true)
    @NotBlank()
    private Integer quantityStock;

    @Schema(name = "price", example = "30.00", required = true)
    @NotBlank()
    private BigDecimal price;
}
