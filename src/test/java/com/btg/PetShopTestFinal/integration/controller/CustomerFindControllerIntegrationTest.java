package com.btg.PetShopTestFinal.integration.controller;

import com.btg.PetShopTestFinal.infra.exception.ClientBadRequest;
import com.btg.PetShopTestFinal.modules.costumers.dto.CustomerResponse;
import com.btg.PetShopTestFinal.modules.costumers.entity.Customer;
import com.btg.PetShopTestFinal.modules.costumers.usecase.FindCustomer;
import com.btg.PetShopTestFinal.utils.CustomerConvert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerFindControllerIntegrationTest {
    @MockBean
    private FindCustomer findCustomer;

    @Autowired
    private MockMvc mockMvc;

    private Customer customer;

    @BeforeEach
    void setup() {
        customer = new Customer();
        customer.setIdTransaction("int-test");
        customer.setName("int-test");
        customer.setEmail("int-test@gmail.com");
    }


    @Test
    void getCustomerByIdSuccessfully() throws Exception {
        CustomerResponse customerResponse = CustomerConvert.toResponse(customer);
        when(findCustomer.findById("int-test")).thenReturn(customerResponse);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/customer/int-test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name")
                        .value("int-test")
        );
    }

    @Test
    void getCustomerByIdWithException() throws Exception {
        when(findCustomer.findById(customer.getIdTransaction()))
                .thenThrow(new ClientBadRequest("Customer not found with ID: " + customer.getIdTransaction()));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/customer/int-test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().is4xxClientError()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.errors")
                        .value("Customer not found with ID: int-test")
        );
    }

    @Test
    void getCustomerByEmailSuccessfully() throws Exception {
        CustomerResponse customerResponse = CustomerConvert.toResponse(customer);
        when(findCustomer.findByEmail("int-test@gmail.com")).thenReturn(customerResponse);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/customer/email/int-test@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email")
                        .value("int-test@gmail.com")
        );
    }

    @Test
    void getCustomerByEmailException() throws Exception {
        when(findCustomer.findByEmail(customer.getEmail()))
                .thenThrow(new ClientBadRequest("Customer not found with email: " + customer.getEmail()));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/customer/email/int-test@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().is4xxClientError()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.errors")
                        .value("Customer not found with email: int-test@gmail.com")
        );
    }

    @Test
    void getCustomerByNameSuccessfully() throws Exception {
        List<Customer> listCustomer = new ArrayList<>();
        Customer customerTest = new Customer();
        customerTest.setIdTransaction("int-test");
        customerTest.setName("int-test");
        customerTest.setEmail("int-test2@gmail.com");

        listCustomer.add(customer);
        listCustomer.add(customerTest);

        when(findCustomer.findByName("int-test"))
                .thenReturn(CustomerConvert.toListResponse(listCustomer));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/customer/name/int-test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].email")
                        .value("int-test@gmail.com")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[1].email")
                        .value("int-test2@gmail.com")
        );
    }

    @Test
    void getCustomerByListName() throws Exception {
        when(findCustomer.findByName("int-test"))
                .thenReturn(CustomerConvert.toListResponse(new ArrayList<>()));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/customer/name/int-test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(  MockMvcResultMatchers.jsonPath(
                "$", Matchers.empty() ));
    }
}
