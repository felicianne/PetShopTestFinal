package com.btg.PetShopTestFinal.modules.customers.usecase;

import com.btg.PetShopTestFinal.infra.exception.ClientBadRequest;
import com.btg.PetShopTestFinal.modules.customers.dto.CustomerRequest;
import com.btg.PetShopTestFinal.modules.customers.dto.CustomerRequestUpdate;
import com.btg.PetShopTestFinal.modules.customers.dto.CustomerResponse;
import com.btg.PetShopTestFinal.modules.customers.entity.Customer;
import com.btg.PetShopTestFinal.modules.customers.repository.CustomerRepository;
import com.btg.PetShopTestFinal.utils.CustomerConvert;
import com.btg.PetShopTestFinal.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateCustomer {
    @Autowired
    CustomerRepository repository;

    public CustomerResponse execute(String id, CustomerRequestUpdate customerRequest) throws Exception {
        Customer customer = repository.findByIdTransaction(id);

        if(customer == null) throw new ClientBadRequest("Customer not found");
        if(!Validator.name(customerRequest.getName()))
            throw new Exception("length must be between 3 and 40");

        customer.setName(customerRequest.getName());
        customer.setAddress(customerRequest.getAddress());
        customer.setIdTransaction(id);
        repository.save(customer);

        return CustomerConvert.toResponse(customer);
    }

}