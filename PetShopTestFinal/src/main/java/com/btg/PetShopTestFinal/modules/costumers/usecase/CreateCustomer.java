package com.btg.PetShopTestFinal.modules.costumers.usecase;

import com.btg.PetShopTestFinal.modules.costumers.entity.Customer;
import com.btg.PetShopTestFinal.modules.costumers.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateCustomer {

    private CustomerRepository repository;

    public CreateCustomer(CustomerRepository repository) {
        this.repository = repository;

        }

        @Transactional
        public void create(Customer customer) {
            repository.save(customer);
        }

        public List<Customer> list() {
            return repository.findAll();
        }


        public List<Customer> findByName(String name) {
            List<Customer> found = new ArrayList<>();
            if (name != null) {
                found = repository.findByName(name);
            }
            return found;
        }

}
