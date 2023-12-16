package com.btg.PetShopTestFinal.modules.order.usecase;

import com.btg.PetShopTestFinal.modules.costumers.entity.Customer;
import com.btg.PetShopTestFinal.modules.costumers.repository.CustomerRepository;
import com.btg.PetShopTestFinal.modules.order.dto.OrderRequest;
import com.btg.PetShopTestFinal.modules.order.dto.OrderResponse;
import com.btg.PetShopTestFinal.modules.order.entity.Order;
import com.btg.PetShopTestFinal.modules.order.repository.OrderRepository;
import com.btg.PetShopTestFinal.utils.OrderConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateOrder{
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CustomerRepository customerRepository;

    public OrderResponse execute(OrderRequest OrderRequest) throws Exception {
        Customer customer = customerRepository.findByIdTransaction(OrderRequest.getCustomerId());

        if(customer == null) throw new Exception("Customer not found");

        Order order = OrderConvert.toEntity(customer);
        orderRepository.save(order);

        return OrderConvert.toResponseOrder(order);
    }
}
