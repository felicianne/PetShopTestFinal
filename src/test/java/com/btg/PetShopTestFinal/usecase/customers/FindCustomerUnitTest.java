package com.btg.PetShopTestFinal.usecase.customers;

import com.btg.PetShopTestFinal.infra.exception.ClientBadRequest;
import com.btg.PetShopTestFinal.modules.costumers.dto.CustomerResponse;
import com.btg.PetShopTestFinal.modules.costumers.entity.Customer;
import com.btg.PetShopTestFinal.modules.costumers.repository.CustomerRepository;
import com.btg.PetShopTestFinal.modules.costumers.usecase.FindCustomer;
import com.btg.PetShopTestFinal.utils.CustomerConvert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class FindCustomerUnitTest {
    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private FindCustomer findCustomer;

    @Test
    public void testFindByEmailWithExistingCustomer() throws ClientBadRequest {
        String email = "test@example.com";
        Customer existingCustomer = new Customer();
        Mockito.when(repository.findByEmail(email)).thenReturn(existingCustomer);

        CustomerResponse result = findCustomer.findByEmail(email);

        assertEquals(CustomerConvert.toResponse(existingCustomer), result);
    }

    @Test
    public void testFindByEmailWithNonExistingCustomer() {

        String email = "nonexistent@example.com";
        Mockito.when(repository.findByEmail(email)).thenReturn(null);

        assertThrows(ClientBadRequest.class, () -> findCustomer.findByEmail(email));
    }

    @Test
    public void testFindByIdWithExistingCustomer() throws ClientBadRequest {

        String customerId = "123";
        Customer existingCustomer = new Customer();
        Mockito.when(repository.findByIdTransaction(customerId)).thenReturn(existingCustomer);

        CustomerResponse result = findCustomer.findById(customerId);

        assertEquals(CustomerConvert.toResponse(existingCustomer), result);
    }

    @Test
    public void testFindByIdWithNonExistingCustomer() {

        String customerId = "456";
        Mockito.when(repository.findByIdTransaction(customerId)).thenReturn(null);

        assertThrows(ClientBadRequest.class, () -> findCustomer.findById(customerId));
    }

    @Test
    public void testFindByName() throws ClientBadRequest {

        String name = "Ana";
        List<Customer> foundCustomers = Collections.singletonList(new Customer());
        Mockito.when(repository.findByName(name)).thenReturn(foundCustomers);

        List<CustomerResponse> result = findCustomer.findByName(name);

        assertEquals(CustomerConvert.toListResponse(foundCustomers), result);
    }

    @Test
    public void testFindByNameWithNullName() {

        assertThrows(ClientBadRequest.class, () -> findCustomer.findByName(null));
    }


}
