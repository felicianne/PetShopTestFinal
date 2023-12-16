package com.btg.PetShopTestFinal.integration;

import com.btg.PetShopTestFinal.modules.costumers.entity.Customer;
import com.btg.PetShopTestFinal.modules.costumers.usecase.RegisterCustomer;
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

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerCreateControllerIntegrationTest {
    @MockBean
    private RegisterCustomer registerCustomer;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() throws Exception {
        Mockito.doAnswer(invocationOnMock -> {
            Customer customer = (Customer) invocationOnMock.getArgument(0);
            customer.setIdTransaction(UUID.randomUUID().toString());
            return CustomerConvert.toResponse(customer);
        }).when(registerCustomer).execute(Mockito.any());
    }

    @Test
    void createCustomerSuccessfully() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/customer")
                        .content("""
          {
            "name": "intone",
            "email": "intone-test@gmail.com",
            "address": "intone-test, 000",
            "password":"@Intone-test123"
          }
          """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.idTransaction").isNotEmpty()
        );
    }

    @Test
    void createCustomerWithLengthNameConditionError() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/customer")
                        .content("""
          {
            "name": "inttwo",
            "email": "inttwo-test@gmail.com",
            "address": "inttwo-test, 000",
            "password":"@Inttwo-test123"
          }
          """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().is4xxClientError()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.errors[0].name")
                        .value("length must be between 3 and 40")
        );
    }

    @Test
    void createCustomerWithEmailError() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/customer")
                        .content("""
          {
            "name": "intthre-test",
            "email": "intthree-test",
            "address": "intthree-test, 000",
            "password":"@Intthree-test123"
          }
          """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().is4xxClientError()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.errors[0].email")
                        .value("must be a legitimate email address")
        );
    }
}
