package com.btg.PetShopTestFinal.utils;


import com.btg.PetShopTestFinal.modules.order.entity.Order;
import com.btg.PetShopTestFinal.modules.orderItem.dto.OrderItemRequest;
import com.btg.PetShopTestFinal.modules.orderItem.dto.OrderItemResponse;
import com.btg.PetShopTestFinal.modules.orderItem.entity.OrderItem;
import com.btg.PetShopTestFinal.modules.product.entity.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderItemConvert {
    public static OrderItem toEntity(OrderItemRequest orderItemRequest, Order order, Product product){
        OrderItem orderItem = new OrderItem();

        orderItem.setIdTransaction(UUID.randomUUID().toString());
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setAmount(orderItemRequest.getAmount());
        orderItem.setPrice(product.getPrice());
        orderItem.setTotal(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getAmount())));
        return orderItem;
    }

    public static OrderItemResponse toResponseOrderItem(OrderItem orderItem){
        OrderItemResponse orderItemResponse = new OrderItemResponse();

        orderItemResponse.setOrderId(orderItem.getOrder().getIdTransaction());
        orderItemResponse.setProduct(orderItem.getProduct());
        orderItemResponse.setAmount(orderItem.getAmount());
        orderItemResponse.setTotal(orderItem.getTotal());

        return orderItemResponse;
    }

    public static List<OrderItemResponse> toResponseList(List<OrderItem> orders){
        List<OrderItemResponse> ordersResponse = new ArrayList<>();

        if(orders == null) return new ArrayList<>();

        for(OrderItem order: orders){
            ordersResponse.add(toResponseOrderItem(order));
        }
        return ordersResponse;
    }
}

