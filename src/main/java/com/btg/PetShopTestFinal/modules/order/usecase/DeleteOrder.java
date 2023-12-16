package com.btg.PetShopTestFinal.modules.order.usecase;

import com.btg.PetShopTestFinal.modules.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteOrder{
    @Autowired
    OrderRepository orderRepository;

    public void execute(String id) throws Exception {
        var order = orderRepository.findOrderById(id);

        if (order == null) {
            throw new Exception("Order not found");
        }

        orderRepository.delete(order);
    }
}

