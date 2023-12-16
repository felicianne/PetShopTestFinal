package com.btg.PetShopTestFinal.modules.order.dto;

import com.btg.PetShopTestFinal.modules.order.entity.OrderStatus;
import com.btg.PetShopTestFinal.modules.orderItem.dto.OrderItemResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data

public class OrderResponse {
    private String idTransaction;
    private String customer;
    private List<OrderItemResponse> items;
    private OrderStatus status;
    private BigDecimal total;

}
