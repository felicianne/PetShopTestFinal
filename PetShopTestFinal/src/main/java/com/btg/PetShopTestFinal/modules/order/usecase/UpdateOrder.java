package com.btg.PetShopTestFinal.modules.order.usecase;

import com.btg.PetShopTestFinal.modules.order.dto.OrderResponse;
import com.btg.PetShopTestFinal.modules.order.entity.Order;
import com.btg.PetShopTestFinal.modules.order.repository.OrderRepository;
import com.btg.PetShopTestFinal.modules.order.utils.CalculateTotal;
import com.btg.PetShopTestFinal.utils.OrderConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UpdateOrder{
    @Autowired
    OrderRepository orderRepository;

    public OrderResponse execute(String orderId, Order orderInput) throws Exception {
        Order order = orderRepository.findOrderById(orderId);

        if(order == null) throw new Exception("Order not found");

        order.setStatus(orderInput.getStatus());
        order.setUpdatedAt(LocalDateTime.now());
        order.setOrderItens(orderInput.getOrderItens());
        order.setTotal(CalculateTotal.execute(orderInput));

        orderRepository.save(order);

        return OrderConvert.toResponseOrder(order);
    }
}
