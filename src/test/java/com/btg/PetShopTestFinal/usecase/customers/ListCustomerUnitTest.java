package com.btg.PetShopTestFinal.usecase.customers;

import com.btg.PetShopTestFinal.modules.customers.dto.CustomerResponse;
import com.btg.PetShopTestFinal.modules.customers.entity.Customer;
import com.btg.PetShopTestFinal.modules.customers.repository.CustomerRepository;
import com.btg.PetShopTestFinal.modules.customers.usecase.ListCustomer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
    public class ListCustomerUnitTest {

        @Mock
        private CustomerRepository repository;

        @InjectMocks
        private ListCustomer listCustomer;


        @Test
        void ListAllCustomers(){
            Customer customerTest = new Customer();
            when(repository.findAll()).thenReturn(List.of(customerTest));

            List<CustomerResponse> listAllCustomerResponse = listCustomer.execute();

            verify(repository, times(1)).findAll();
            assertEquals(1, listAllCustomerResponse.size());

        }
}
