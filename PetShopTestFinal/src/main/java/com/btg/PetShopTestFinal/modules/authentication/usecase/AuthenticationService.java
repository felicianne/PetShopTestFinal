package com.btg.PetShopTestFinal.modules.authentication.usecase;

import com.btg.PetShopTest.modules.customers.entity.Customer;
import com.btg.PetShopTest.modules.customers.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) throw new UsernameNotFoundException("Customer not found");
        return customer;
    }
}
