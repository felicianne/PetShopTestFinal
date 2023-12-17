package com.btg.PetShopTestFinal.integration.controller;

import com.btg.PetShopTestFinal.modules.costumers.dto.CustomerRequestUpdate;
import com.btg.PetShopTestFinal.modules.costumers.entity.Customer;
import com.btg.PetShopTestFinal.modules.costumers.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerUpdateControllerIntegrationTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void updateCustomerSuccessfully() throws Exception {
        Customer customer = new Customer();
        customer.setIdTransaction("int-test");
        customer.setName("int-test");
        customer.setEmail("int-test@gmail.com");
        customer.setAddress("int-test, 000");
        customer.setPassword("Int-test1");
        customerRepository.save(customer);

        CustomerRequestUpdate customerUpdate = new CustomerRequestUpdate();
        customerUpdate.setName("John Doe");
        customerUpdate.setAddress("123 Main Street");

        String customerRequest = mapper.writeValueAsString(customerUpdate);

        mockMvc.perform(MockMvcRequestBuilders.put("/customer/" + customer.getIdTransaction())
                        .content(customerRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value("John Doe")
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.address").value("123 Main Street")
                );
    }
}
