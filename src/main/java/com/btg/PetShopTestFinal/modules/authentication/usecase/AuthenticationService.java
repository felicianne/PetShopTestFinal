package com.btg.PetShopTestFinal.modules.authentication.usecase;


import com.btg.PetShopTestFinal.infra.exception.ClientBadRequest;
import com.btg.PetShopTestFinal.modules.costumers.entity.Customer;
import com.btg.PetShopTestFinal.modules.costumers.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer loadUserByUsername(String email) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) try {
            throw new ClientBadRequest("Customer not found");
        } catch (ClientBadRequest e) {
            throw new RuntimeException(e.getMessage());
        }
        return customer;
    }
}
