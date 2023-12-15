package com.btg.PetShopTestFinal.modules.costumers.usecase;

import com.btg.PetShopTestFinal.infra.exception.ClientBadRequest;
import com.btg.PetShopTestFinal.infra.exception.PasswordValidationError;
import com.btg.PetShopTestFinal.modules.costumers.dto.CustomerResponse;
import com.btg.PetShopTestFinal.modules.costumers.entity.Customer;
import com.btg.PetShopTestFinal.modules.costumers.repository.CustomerRepository;
import com.btg.PetShopTestFinal.utils.CustomerConvert;
import com.btg.PetShopTestFinal.utils.Validador;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterCustomer {
    private final CustomerRepository repository;
    private final PasswordEncoder passwordEncoder;

    public CustomerResponse execute(Customer customer) throws Exception {
        if(!Validador.name(customer.getName()))
            throw new Exception("length must be between 3 and 35");

        if(!Validador.passwordValidate(customer.getPassword()))
            throw new PasswordValidationError("Senha deve seguir o padrão");

        checkEmailAvailability(customer.getEmail());

        String encodePassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodePassword);

        repository.save(customer);
        return CustomerConvert.toResponse(customer);
    }

    private void checkEmailAvailability(String email) throws Exception {
        if(!Validador.emailValidate(email))
            throw new Exception("must be a well-formed email address");

        Customer emailExist = repository.findByEmail(email);
        if  (emailExist != null ) {
            throw new ClientBadRequest("Email já está em uso");
        }
    }
}
