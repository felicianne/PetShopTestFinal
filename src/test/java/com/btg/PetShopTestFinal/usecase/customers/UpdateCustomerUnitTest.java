package com.btg.PetShopTestFinal.usecase.customers;


import com.btg.PetShopTestFinal.modules.customers.dto.CustomerRequestUpdate;
import com.btg.PetShopTestFinal.modules.customers.dto.CustomerResponse;
import com.btg.PetShopTestFinal.modules.customers.entity.Customer;
import com.btg.PetShopTestFinal.modules.customers.repository.CustomerRepository;
import com.btg.PetShopTestFinal.modules.customers.usecase.UpdateCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
 class UpdateCustomerUnitTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private UpdateCustomer updateCustomer;

    private Customer customer;

    @BeforeEach
    public void setup() {
        customer = new Customer();
        customer.setIdTransaction("unit-test");
        customer.setEmail("validEmail@email.com");
        customer.setAddress("validAddress,999");
        customer.setName("ValidName");
        customer.setPassword("@validPassword123");
    }

    @Test
    public void UpdateCustomerSuccess() throws Exception {
        CustomerRequestUpdate customerRequest = new CustomerRequestUpdate();
        customerRequest.setName("unit test");
        customerRequest.setAddress("street uni test, 000");

        when(repository.findByIdTransaction(customer.getIdTransaction())).thenReturn(customer);

        CustomerResponse customerResponse = updateCustomer.execute("unit-test", customerRequest);

        verify(repository, times(1)).save(any());
        assertEquals("unit test", customerResponse.getName());
    }

    @Test
    @DisplayName("Should update product invalid")
    void UpdateProductIdInvalid() {
        CustomerRequestUpdate customerRequest = new CustomerRequestUpdate();

        when(repository.findByIdTransaction("uni-test")).thenReturn(new Customer());

        Customer customer = repository.findByIdTransaction("uni-test");

        Exception exception = assertThrows(
                Exception.class, () -> updateCustomer.execute(customer.getIdTransaction(), customerRequest));
        assertEquals("Customer not found", exception.getMessage());
    }
}


