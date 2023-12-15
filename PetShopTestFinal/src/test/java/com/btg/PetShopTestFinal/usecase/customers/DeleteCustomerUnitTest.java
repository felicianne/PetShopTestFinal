package com.btg.PetShopTestFinal.usecase.customers;

import com.btg.PetShopTestFinal.infra.exception.ClientBadRequest;
import com.btg.PetShopTestFinal.modules.costumers.entity.Customer;
import com.btg.PetShopTestFinal.modules.costumers.repository.CustomerRepository;
import com.btg.PetShopTestFinal.modules.costumers.usecase.DeleteCustomer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
    public class DeleteCustomerUnitTest {

        @Mock
        private CustomerRepository repository;

        @InjectMocks
        private DeleteCustomer deleteCustomer;

        @Test
        public void ExecuteWithExistingCustomer() throws Exception {
            String customerId = "123";
            Customer existingCustomer = new Customer();
            Mockito.when(repository.findByIdTransaction(customerId)).thenReturn(existingCustomer);
        }
        @Test
        public void testExecuteWithNonExistingCustomer() {
            String customerId = "456";
            Mockito.when(repository.findByIdTransaction(customerId)).thenReturn(null);

            assertThrows(ClientBadRequest.class, () -> deleteCustomer.execute(customerId));
        }

    }
