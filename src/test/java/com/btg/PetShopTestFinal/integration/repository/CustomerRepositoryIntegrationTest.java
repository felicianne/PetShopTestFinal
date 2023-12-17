package com.btg.PetShopTestFinal.integration.repository;

import com.btg.PetShopTestFinal.modules.costumers.entity.Customer;
import com.btg.PetShopTestFinal.modules.costumers.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

    @DataJpaTest
    public class CustomerRepositoryIntegrationTest {
        @Autowired
        private TestEntityManager testEntityManager;

        @Autowired
        private CustomerRepository customerRepository;

        @Test
        public void ShouldFindCustomerByEmail(){
            Customer customerTest = new Customer();
            customerTest.setIdTransaction(UUID.randomUUID().toString());
            customerTest.setEmail("ana@email.com");
            customerTest.setName("Ana");
            customerTest.setPassword("@UnitTest123");

            customerTest = testEntityManager.persist(customerTest);

            Customer customer= customerRepository.findByEmail(customerTest.getEmail());

            assertEquals(customerTest.getName(),customer.getName());
            assertEquals(customerTest.getEmail(),customer.getEmail());

        }

        @Test
        public void FindCustomerByName(){
            final Customer customerTestOne = new Customer();
            customerTestOne.setIdTransaction(UUID.randomUUID().toString());
            customerTestOne.setEmail("ana@email.com");
            customerTestOne.setName("Ana");
            customerTestOne.setPassword("@UnitTest123");

            final Customer customerTestTwo = new Customer();
            customerTestTwo.setIdTransaction(UUID.randomUUID().toString());
            customerTestTwo.setEmail("joana@email.com");
            customerTestTwo.setName("Joana");
            customerTestTwo.setPassword("@UnitTest123");

            testEntityManager.persist(customerTestOne);
            testEntityManager.persist(customerTestTwo);

            List<Customer> listCustomer = (List<Customer>) customerRepository.findByName("test");

            assertEquals(2, listCustomer.size());

            Customer customerFoundOne = listCustomer.stream()
                    .filter(customer -> customer.getName().equals(customerTestOne.getName()))
                    .findFirst()
                    .orElseThrow();

            assertEquals(customerTestOne.getName(), customerFoundOne.getName());
            assertEquals(customerTestOne.getEmail(), customerFoundOne.getEmail());


            Customer customerFoundTwo = listCustomer.stream()
                    .filter(customer -> customer.getName().equals(customerTestTwo.getName()))
                    .findFirst()
                    .orElseThrow();

            assertEquals(customerTestTwo.getName(), customerFoundTwo.getName());
            assertEquals(customerTestTwo.getEmail(), customerFoundTwo.getEmail());

        }

        @Test
        public void FindCustomerById(){
            Customer customerTest = new Customer();
            customerTest.setIdTransaction(UUID.randomUUID().toString());
            customerTest.setEmail("ana@email.com");
            customerTest.setName("uana");
            customerTest.setPassword("@UnitTest123");

            customerTest = testEntityManager.persist(customerTest);

            Customer customer= customerRepository.findByIdTransaction(customerTest.getIdTransaction());

            assertEquals(customerTest.getName(),customer.getName());
            assertEquals(customerTest.getEmail(),customer.getEmail());

        }
    }

