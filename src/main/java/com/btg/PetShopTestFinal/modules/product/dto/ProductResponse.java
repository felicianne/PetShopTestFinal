package com.btg.PetShopTestFinal.modules.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductResponse {
    @Schema(name = "Product ID", example = "1", required = true)
    private String skuId;

    @Schema(name = "name", example = "console-ps5", required = true)
    private String name;

    @Schema(name = "quantity", example = "50", required = true)
    @NotBlank()
    private Integer quantityStock;

    @Schema(name = "price", example = "125.56", required = true)
    private BigDecimal price;


}
