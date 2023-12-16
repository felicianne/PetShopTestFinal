package com.btg.PetShopTestFinal.modules.costumers.usecase;

import com.btg.PetShopTestFinal.infra.exception.ClientBadRequest;
import com.btg.PetShopTestFinal.modules.costumers.dto.CustomerResponse;
import com.btg.PetShopTestFinal.modules.costumers.entity.Customer;
import com.btg.PetShopTestFinal.modules.costumers.repository.CustomerRepository;
import com.btg.PetShopTestFinal.utils.CustomerConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindCustomer {
    private final CustomerRepository repository;

    public CustomerResponse findByEmail(String email) throws ClientBadRequest {
        Customer customer = repository.findByEmail(email);

        if(customer == null){
            throw new ClientBadRequest("Customer not found with email: " + email);
        }
        return CustomerConvert.toResponse(customer);
    }

    public CustomerResponse findById(String id) throws ClientBadRequest {
        Customer customer = repository.findByIdTransaction(id);

        if(customer == null){
            throw new ClientBadRequest("Customer not found with ID: " + id);
        }

        return CustomerConvert.toResponse(customer);
    }

    public List<CustomerResponse> findByName(String name) throws ClientBadRequest {

        if (name == null) {
            throw new ClientBadRequest("Name not null");
        }

        List<Customer> found = (List<Customer>) repository.findByName(name);

        return CustomerConvert.toListResponse(found);
    }
}