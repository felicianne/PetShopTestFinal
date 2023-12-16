package com.btg.PetShopTestFinal.modules.orderItem.usecase;

import com.btg.PetShopTestFinal.modules.orderItem.entity.OrderItem;
import com.btg.PetShopTestFinal.modules.orderItem.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteOrderItem {
    @Autowired
    OrderItemRepository orderItemRepository;

    public void execute(String id) throws Exception {
        OrderItem orderItem = orderItemRepository.findOrderItemById(id);

        if(orderItem == null) throw  new Exception("Order not found");

        orderItemRepository.delete(orderItem);
    }
}