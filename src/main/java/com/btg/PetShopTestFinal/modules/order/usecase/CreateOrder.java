package com.btg.PetShopTestFinal.modules.order.usecase;

import com.btg.PetShopTestFinal.infra.exception.ClientBadRequest;
import com.btg.PetShopTestFinal.modules.customers.entity.Customer;
import com.btg.PetShopTestFinal.modules.customers.repository.CustomerRepository;
import com.btg.PetShopTestFinal.modules.order.dto.OrderRequest;
import com.btg.PetShopTestFinal.modules.order.dto.OrderResponse;
import com.btg.PetShopTestFinal.modules.order.entity.Order;
import com.btg.PetShopTestFinal.modules.order.repository.OrderRepository;
import com.btg.PetShopTestFinal.utils.OrderConvert;
import org.springframework.stereotype.Service;

@Service
public class CreateOrder {

    private OrderRepository repository;
    private CustomerRepository customerRepository;

    public OrderResponse execute(OrderRequest OrderRequest) throws Exception {
        Customer customer = customerRepository.findByIdTransaction(OrderRequest.getCustomerId());

        if(customer == null) throw new ClientBadRequest("Customer not found");

        Order order = OrderConvert.toEntity(customer);
        repository.save(order);

        return OrderConvert.toResponseOrder(order);
    }
}


