package com.btg.PetShopTestFinal.usecase.customers;

import com.btg.PetShopTestFinal.infra.exception.ClientBadRequest;
import com.btg.PetShopTestFinal.infra.exception.PasswordValidationError;
import com.btg.PetShopTestFinal.modules.costumers.dto.CustomerResponse;
import com.btg.PetShopTestFinal.modules.costumers.entity.Customer;
import com.btg.PetShopTestFinal.modules.costumers.repository.CustomerRepository;
import com.btg.PetShopTestFinal.modules.costumers.usecase.RegisterCustomer;
import com.btg.PetShopTestFinal.utils.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class RegisterCustomerUnitTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterCustomer registerCustomer;

    private Customer customer;

    @BeforeEach
    public void setup(){
        customer = new Customer();
        customer.setName("Ana");
        customer.setEmail("ana@exemple.com");
        customer.setPassword("password");
    }


    @Test
    public void testExecuteWithValidCustomer() throws Exception {

        Mockito.when(Validator.name(this.customer.getName())).thenReturn(true);
        Mockito.when(Validator.passwordValidate(this.customer.getPassword())).thenReturn(true);
        Mockito.when(Validator.emailValidate(this.customer.getEmail())).thenReturn(true);
        Mockito.when(repository.findByEmail(this.customer.getEmail())).thenReturn(null);
        Mockito.when(passwordEncoder.encode(this.customer.getPassword())).thenReturn("encodedPassword");

        CustomerResponse result = registerCustomer.execute(customer);

        CustomerResponse expected = new CustomerResponse();

        assertEquals(expected, result);
    }

    @Test
    public void testExecuteWithInvalidName() {

        customer.setName("Joana");
        Mockito.when(Validator.name(this.customer.getName())).thenReturn(false);

        assertThrows(Exception.class, () -> registerCustomer.execute(this.customer));
    }

    @Test
    public void testExecuteWithoutdName() {

        customer.setName("");
        Mockito.when(Validator.name(this.customer.getName())).thenReturn(null);

        assertThrows(Exception.class, () -> registerCustomer.execute(this.customer));
    }


    @Test
    public void testExecuteWithInvalidPassword() {

        customer.setPassword("wrongPassword");
        Mockito.when(Validator.passwordValidate(this.customer.getPassword())).thenReturn(false);

        assertThrows(PasswordValidationError.class, () -> registerCustomer.execute(this.customer));
    }

    @Test
    public void testExecuteWithoutPassword() {

        customer.setPassword("");
        Mockito.when(Validator.passwordValidate(this.customer.getPassword())).thenReturn(null);

        assertThrows(PasswordValidationError.class, () -> registerCustomer.execute(this.customer));
    }

    @Test
    public void testExecuteWithInvalidEmail() {

        customer.setEmail("anaexemple.com");
        Mockito.when(Validator.emailValidate(this.customer.getEmail())).thenReturn(false);

        assertThrows(Exception.class, () -> registerCustomer.execute(customer));

        Mockito.verify(repository, Mockito.times(1)).findByEmail("anaexemple.com");
    }

    @Test
    public void testExecuteWithoutEmail() {

        customer.setEmail("");
        Mockito.when(Validator.emailValidate(this.customer.getEmail())).thenReturn(null);
        Mockito.when(repository.findByEmail(this.customer.getEmail())).thenReturn(new Customer());

        Mockito.verify(repository, Mockito.times(1)).findByEmail("");


        assertThrows(ClientBadRequest.class, () -> registerCustomer.execute(customer));
    }

}


