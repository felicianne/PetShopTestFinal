package com.btg.PetShopTestFinal.integration.repository;

import com.btg.PetShopTestFinal.modules.customers.entity.Customer;
import com.btg.PetShopTestFinal.modules.customers.repository.CustomerRepository;
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
        customerTest.setEmail("unit-test@email.com");
        customerTest.setAddress("unit-test-address,999");
        customerTest.setName("unit test");
        customerTest.setPassword("@UnitTest123");

        customerTest = testEntityManager.persist(customerTest);

        Customer customer= customerRepository.findByEmail(customerTest.getEmail());

        assertEquals(customerTest.getName(),customer.getName());
        assertEquals(customerTest.getEmail(),customer.getEmail());
        assertEquals(customerTest.getAddress(),customer.getAddress());
    }

//    @Test
//    public void ShouldFindCustomerByName(){
//        final Customer customerTestOne = new Customer();
//        customerTestOne.setIdTransaction(UUID.randomUUID().toString());
//        customerTestOne.setEmail("unit-test@email.com");
//        customerTestOne.setAddress("unit-test-address,999");
//        customerTestOne.setName("unit test");
//        customerTestOne.setPassword("@UnitTest123");
//
//        final Customer customerTestTwo = new Customer();
//        customerTestTwo.setIdTransaction(UUID.randomUUID().toString());
//        customerTestTwo.setEmail("unit-test-Two@email.com");
//        customerTestTwo.setAddress("unit-test-address,999");
//        customerTestTwo.setName("unit test Two");
//        customerTestTwo.setPassword("@UnitTest123");
//
//        testEntityManager.persist(customerTestOne);
//        testEntityManager.persist(customerTestTwo);
//
//        List<Customer> listCustomer = customerRepository.findByName("test");
//
//        assertEquals(2, listCustomer.size());
//
//        Customer customerFoundOne = listCustomer.stream()
//                .filter(customer -> customer.getName().equals(customerTestOne.getName()))
//                .findFirst()
//                .orElseThrow(); // Isso é necessário para evitar NullPointerException se não encontrar
//
//        assertEquals(customerTestOne.getName(), customerFoundOne.getName());
//        assertEquals(customerTestOne.getEmail(), customerFoundOne.getEmail());
//        assertEquals(customerTestOne.getAddress(), customerFoundOne.getAddress());
//
//
//        Customer customerFoundTwo = listCustomer.stream()
//                .filter(customer -> customer.getName().equals(customerTestTwo.getName()))
//                .findFirst()
//                .orElseThrow();
//
//        assertEquals(customerTestTwo.getName(), customerFoundTwo.getName());
//        assertEquals(customerTestTwo.getEmail(), customerFoundTwo.getEmail());
//        assertEquals(customerTestTwo.getAddress(), customerFoundTwo.getAddress());
//    }

    @Test
    public void ShouldFindCustomerById(){
        Customer customerTest = new Customer();
        customerTest.setIdTransaction(UUID.randomUUID().toString());
        customerTest.setEmail("unit-test@email.com");
        customerTest.setAddress("unit-test-address,999");
        customerTest.setName("unit test");
        customerTest.setPassword("@UnitTest123");

        customerTest = testEntityManager.persist(customerTest);

        Customer customer= customerRepository.findByIdTransaction(customerTest.getIdTransaction());

        assertEquals(customerTest.getName(),customer.getName());
        assertEquals(customerTest.getEmail(),customer.getEmail());
        assertEquals(customerTest.getAddress(),customer.getAddress());
    }
}

