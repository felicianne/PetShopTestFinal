package com.btg.PetShopTestFinal.integration.controller;

import com.btg.PetShopTestFinal.modules.customers.dto.CustomerResponse;
import com.btg.PetShopTestFinal.modules.customers.entity.Customer;
import com.btg.PetShopTestFinal.modules.customers.usecase.ListCustomer;
import com.btg.PetShopTestFinal.utils.CustomerConvert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerGetAllControllerIntegrationTest {
    @MockBean
    private ListCustomer listCustomer;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        Customer customer = new Customer();
        customer.setIdTransaction(UUID.randomUUID().toString());
        customer.setName("int-test");
        customer.setEmail("int-test@gmail.com");
        List<CustomerResponse> customerResponse = CustomerConvert.toListResponse(List.of(customer));
        Mockito.when(listCustomer.execute()).thenReturn(customerResponse);
    }


    @Test
    void getAllCustomersSuccessfully() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name")
                        .value("int-test")
        );
    }
}
