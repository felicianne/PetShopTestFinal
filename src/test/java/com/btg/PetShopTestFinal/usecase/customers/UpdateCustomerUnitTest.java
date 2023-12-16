package com.btg.PetShopTestFinal.usecase.customers;

import com.btg.PetShopTestFinal.modules.costumers.dto.CustomerRequestUpdate;
import com.btg.PetShopTestFinal.modules.costumers.dto.CustomerResponse;
import com.btg.PetShopTestFinal.modules.costumers.entity.Customer;
import com.btg.PetShopTestFinal.modules.costumers.repository.CustomerRepository;
import com.btg.PetShopTestFinal.modules.costumers.usecase.UpdateCustomer;
import org.junit.jupiter.api.BeforeEach;
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

}

       /* @Test
        public void testExecuteWithExistingCustomer() throws Exception {

            String customerId = "123";
            Customer existingCustomer = new Customer();
            Mockito.when(repository.findByIdTransaction(customerId)).thenReturn(existingCustomer);

            CustomerResponse result = updateCustomer.execute(customerId, customerRequest);

            assertEquals(CustomerConvert.toResponse(existingCustomer), result);
            assertEquals("New Name", existingCustomer.getName());
            assertEquals("New Address", existingCustomer.getAddress());
        }

        @Test
        public void testExecuteWithNonExistingCustomer() {

            String customerId = "456";
            Mockito.when(repository.findByIdTransaction(customerId)).thenReturn(null);

            assertThrows(Exception.class, () -> updateCustomer.execute(customerId, customerRequest));
        }

        @Test
        public void testExecuteWithNullId() {

            assertThrows(Exception.class, () -> updateCustomer.execute(null, customerRequest));
        }

        @Test
        public void testExecuteWithNullCustomerRequest() {

            String customerId = "789";

            assertThrows(Exception.class, () -> updateCustomer.execute(customerId, null));
        }
    }
*/


