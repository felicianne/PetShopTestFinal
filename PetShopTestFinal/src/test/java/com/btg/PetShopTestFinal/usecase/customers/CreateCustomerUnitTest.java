package com.btg.PetShopTestFinal.usecase.customers;

import com.btg.PetShopTestFinal.modules.costumers.entity.Customer;
import com.btg.PetShopTestFinal.modules.costumers.repository.CustomerRepository;
import com.btg.PetShopTestFinal.modules.costumers.usecase.CreateCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CreateCustomerUnitTest {

    @InjectMocks
    private CreateCustomer createCustomer;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCustomer() {

        Customer customer = new Customer();

        createCustomer.create(customer);

        verify(customerRepository, times(1)).save(customer);
    }

        @Test
        public void testListCustomers() {

            List<Customer> mockCustomers = new ArrayList<>();
            when(customerRepository.findAll()).thenReturn(mockCustomers);

            List<Customer> result = createCustomer.list();

            assertEquals(mockCustomers, result);
        }

        @Test
        public void testFindCustomersByName() {

            String name = "Ana";
            List<Customer> mockFoundCustomers = new ArrayList<>();
            when(customerRepository.findByName(name)).thenReturn(mockFoundCustomers);

            List<Customer> result = createCustomer.findByName(name);

            assertEquals(mockFoundCustomers, result);
        }

        @Test
        public void testFindCustomersByNameWithNullName() {

            List<Customer> result = createCustomer.findByName(null);

            assertTrue(result.isEmpty());
            verify(customerRepository, never()).findByName(anyString());
        }

    }

