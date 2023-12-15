package com.btg.PetShopTestFinal.modules.costumers.usecase;

import com.btg.PetShopTestFinal.modules.costumers.dto.CustomerRequest;
import com.btg.PetShopTestFinal.modules.costumers.dto.CustomerResponse;
import com.btg.PetShopTestFinal.modules.costumers.entity.Customer;
import com.btg.PetShopTestFinal.modules.costumers.repository.CustomerRepository;
import com.btg.PetShopTestFinal.utils.CustomerConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateCustomer {
    @Autowired
    CustomerRepository repository;

    public CustomerResponse execute(String id, CustomerRequest customerRequest) throws Exception {
        Customer customer = repository.findByIdTransaction(id);

        if(customer == null) throw new Exception("Customer not found");

        customer.setName(customerRequest.getName());
        customer.setAddress(customerRequest.getAddress());
        customer.setIdTransaction(id);
        repository.save(customer);

        return CustomerConvert.toResponse(customer);
    }
}