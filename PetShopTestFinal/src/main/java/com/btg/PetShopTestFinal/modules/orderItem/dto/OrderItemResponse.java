package com.btg.PetShopTestFinal.modules.orderItem.dto;

import com.btg.PetShopTestFinal.modules.product.entity.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {
    private String orderId;
    private Product product;
    private Integer amount;
    private BigDecimal total;
}
