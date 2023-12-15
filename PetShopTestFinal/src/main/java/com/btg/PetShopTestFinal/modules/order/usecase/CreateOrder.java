package com.btg.PetShopTestFinal.modules.order.usecase;

import com.btg.PetShopTestFinal.modules.costumers.entity.Customer;
import com.btg.PetShopTestFinal.modules.costumers.repository.CustomerRepository;
import com.btg.PetShopTestFinal.modules.order.entity.Order;
import com.btg.PetShopTestFinal.modules.order.entity.OrderStatus;
import com.btg.PetShopTestFinal.modules.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

    @Service
    public class CreateOrder {

        private OrderRepository repository;
        private CustomerRepository customerRepository;

        public CreateOrder(OrderRepository repository, CustomerRepository customerRepository) {
            this.repository = repository;
            this.customerRepository = customerRepository;
        }

        @Transactional
        public Order create(Customer customer) {
            validCustomer(customer);
            Order order = new Order();
            order.setCustomer(customer);
            order.setOrderItens(new ArrayList<>());
            order.setStatus(OrderStatus.OPEN);
            repository.save(order);
            return order;
        }

        private void validCustomer(Customer customer) {
            Customer found = customerRepository.findByName(customer.getName());
            if (found == null) {
                throw new IllegalStateException("Cliente não encontrado");
            }
        }

    }


