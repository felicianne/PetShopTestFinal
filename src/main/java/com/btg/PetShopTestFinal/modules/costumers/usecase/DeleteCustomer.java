package com.btg.PetShopTestFinal.modules.costumers.usecase;

import com.btg.PetShopTestFinal.infra.exception.ClientBadRequest;
import com.btg.PetShopTestFinal.modules.costumers.entity.Customer;
import com.btg.PetShopTestFinal.modules.costumers.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCustomer {
    private final CustomerRepository repository;

    public void execute(String id) throws Exception {
        Customer customer = repository.findByIdTransaction(id);
        if (customer == null) throw new ClientBadRequest("Customer not found with ID: " + id);
        repository.delete(customer);
    }
}
