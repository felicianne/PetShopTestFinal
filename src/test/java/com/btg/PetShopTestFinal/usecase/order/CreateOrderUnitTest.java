package com.btg.PetShopTestFinal.usecase.order;

import com.btg.PetShopTestFinal.modules.costumers.entity.Customer;
import com.btg.PetShopTestFinal.modules.costumers.repository.CustomerRepository;

import com.btg.PetShopTestFinal.modules.order.dto.OrderRequest;
import com.btg.PetShopTestFinal.modules.order.dto.OrderResponse;
import com.btg.PetShopTestFinal.modules.order.entity.Order;
import com.btg.PetShopTestFinal.modules.order.repository.OrderRepository;
import com.btg.PetShopTestFinal.modules.order.usecase.CreateOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CreateOrderUnitTest {

    @InjectMocks
    private CreateOrder createOrder;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    private List<Customer> customer;


    @BeforeEach
    public void setUp() {
        customer = (List<Customer>) new Customer();

        //customer = List.of(new Customer());


        when(customerRepository.findByName(Mockito.any())).thenReturn((Customer) customer);

    }

    @Test
    public void testInvalidCustomerMustReturnException() {


        when(customerRepository.findByName(Mockito.any())).thenReturn(null);

        assertThrows(IllegalStateException.class,
                () -> createOrder.execute((OrderRequest) customer));
    }

    @Test
    public void testExecuteOrderSuccessfully() throws Exception {

        OrderRequest orderRequest = new OrderRequest();
        Customer mockCustomer = new Customer();
        when(customerRepository.findByIdTransaction(orderRequest.getCustomerId())).thenReturn(mockCustomer);

        OrderResponse orderResponse = createOrder.execute(orderRequest);

        assertNotNull(orderResponse);

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testExecuteOrderCustomerNotFound() {

        OrderRequest orderRequest = new OrderRequest();
        when(customerRepository.findByIdTransaction(orderRequest.getCustomerId())).thenReturn(null);

        assertThrows(Exception.class, () -> createOrder.execute(orderRequest));

        verify(orderRepository, never()).save(any(Order.class));
    }

}