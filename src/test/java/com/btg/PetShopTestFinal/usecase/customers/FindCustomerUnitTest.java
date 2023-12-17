package com.btg.PetShopTestFinal.usecase.customers;

import com.btg.PetShopTestFinal.infra.exception.ClientBadRequest;


import com.btg.PetShopTestFinal.modules.customers.dto.CustomerResponse;
import com.btg.PetShopTestFinal.modules.customers.entity.Customer;
import com.btg.PetShopTestFinal.modules.customers.repository.CustomerRepository;
import com.btg.PetShopTestFinal.modules.customers.usecase.FindCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class FindCustomerUnitTest {
    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private FindCustomer findCustomer;

    private Customer customer;

    @BeforeEach
    public void setup(){
        customer = new Customer();
        customer.setIdTransaction("unit-test");
        customer.setEmail("validEmail@email.com");
        customer.setAddress("validAddress,999");
        customer.setName("ValidName");
        customer.setPassword("@validPassword123");
    }

    @Test
    public void findCustomerById() throws ClientBadRequest {
        when(repository.findByIdTransaction(customer.getIdTransaction())).thenReturn(customer);

        CustomerResponse customerTest = findCustomer.findById(customer.getIdTransaction());

        verify(repository, times(1)).findByIdTransaction(any());
        assertEquals(customer.getIdTransaction(), customerTest.getIdTransaction(), "Unexpected customer id");
    }

    @Test
    public void findCustomerByInvalidId(){
        when(repository.findByIdTransaction(customer.getIdTransaction())).thenReturn(null);

        var execption = assertThrows(Exception.class, () -> findCustomer.findById(customer.getIdTransaction()));

        assertEquals("Customer not found with ID: " + "unit-test", execption.getMessage());
        verify(repository, times(1)).findByIdTransaction(any());
    }

    @Test
    public void findCustomerByEmail() throws ClientBadRequest {
        when(repository.findByEmail(customer.getEmail())).thenReturn(customer);

        CustomerResponse customerTest = findCustomer.findByEmail(customer.getEmail());

        verify(repository, times(1)).findByEmail(any());
        assertEquals("validEmail@email.com", customerTest.getEmail(), "Unexpected customer email");
    }

    @Test
    public void findCustomerByInvalidEmail(){
        when(repository.findByEmail(customer.getEmail())).thenReturn(null);

        var execption = assertThrows(Exception.class, () -> findCustomer.findByEmail(customer.getEmail()));
        assertEquals("Customer not found with email: " + "validEmail@email.com", execption.getMessage());
        verify(repository, times(1)).findByEmail(any());
    }


    @Test
    public void findCustomerByNameEqualsNull(){
        when(repository.findByName(null)).thenReturn(null);

        var execption = assertThrows(Exception.class, () ->  findCustomer.findByName(null));

        assertEquals("Name not null", execption.getMessage());
    }
}