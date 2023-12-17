package com.btg.PetShopTestFinal.modules.customers.usecase;

import com.btg.PetShopTestFinal.infra.exception.ClientBadRequest;
import com.btg.PetShopTestFinal.infra.exception.PasswordValidationError;
import com.btg.PetShopTestFinal.modules.customers.dto.CustomerResponse;
import com.btg.PetShopTestFinal.modules.customers.entity.Customer;
import com.btg.PetShopTestFinal.modules.customers.repository.CustomerRepository;
import com.btg.PetShopTestFinal.utils.CustomerConvert;
import com.btg.PetShopTestFinal.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterCustomer {
    private final CustomerRepository repository;
    private final PasswordEncoder passwordEncoder;

    public CustomerResponse execute(Customer customer) throws Exception {
        if(!Validator.name(customer.getName()))
            throw new Exception("length must be between 3 and 40");

        if(!Validator.passwordValidate(customer.getPassword()))
            throw new PasswordValidationError("Incorrect Password");

        checkEmailAvailability(customer.getEmail());

        String encodePassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodePassword);

        repository.save(customer);
        return CustomerConvert.toResponse(customer);
    }

    private void checkEmailAvailability(String email) throws Exception {
        if(!Validator.emailValidate(email))
            throw new Exception("must be a legitimate email address");

        Customer emailExist = repository.findByEmail(email);
        if  (emailExist != null ) {
            throw new ClientBadRequest("Email is already in use");
        }
    }
}
