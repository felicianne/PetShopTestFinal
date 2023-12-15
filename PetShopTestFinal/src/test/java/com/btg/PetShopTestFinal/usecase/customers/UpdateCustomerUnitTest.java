package com.btg.PetShopTestFinal.usecase.customers;

import com.btg.PetShopTestFinal.modules.costumers.dto.CustomerRequest;
import com.btg.PetShopTestFinal.modules.costumers.dto.CustomerResponse;
import com.btg.PetShopTestFinal.modules.costumers.entity.Customer;
import com.btg.PetShopTestFinal.modules.costumers.repository.CustomerRepository;
import com.btg.PetShopTestFinal.modules.costumers.usecase.UpdateCustomer;
import com.btg.PetShopTestFinal.utils.CustomerConvert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
    public class UpdateCustomerUnitTest {

        @Mock
        private CustomerRepository repository;

        @InjectMocks
        private UpdateCustomer updateCustomer;

        private CustomerRequest customerRequest;

        @BeforeEach
        public void setup() {
            customerRequest = new CustomerRequest();
            customerRequest.setAddress("New Name");
            customerRequest.setAddress("New Address");
        }


        @Test
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
}


