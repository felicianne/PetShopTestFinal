package com.btg.PetShopTestFinal.usecase.customers;

import com.btg.PetShopTestFinal.infra.exception.ClientBadRequest;

import com.btg.PetShopTestFinal.modules.customers.entity.Customer;
import com.btg.PetShopTestFinal.modules.customers.repository.CustomerRepository;
import com.btg.PetShopTestFinal.modules.customers.usecase.DeleteCustomer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
    public class DeleteCustomerUnitTest {

        @Mock
        private CustomerRepository repository;

        @InjectMocks
        private DeleteCustomer deleteCustomer;

    @Test
    public void deleteCustomerSuccess() throws Exception {
        Customer customer = new Customer();
        customer.setIdTransaction(UUID.randomUUID().toString());

        when(repository.findByIdTransaction(customer.getIdTransaction())).thenReturn(customer);

        deleteCustomer.execute(customer.getIdTransaction());

        verify(repository, times(1)).delete(customer);
    }

    @Test
    public void deleteCustomerEquals(){
        ClientBadRequest exception = assertThrows(
                ClientBadRequest.class, () -> deleteCustomer.execute("unit-test"));

        assertEquals("Customer not found with ID: unit-test", exception.getMessage());
    }
}
