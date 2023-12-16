package com.btg.PetShopTestFinal.usecase.order;

import com.btg.PetShopTestFinal.modules.order.dto.OrderResponse;
import com.btg.PetShopTestFinal.modules.order.entity.Order;
import com.btg.PetShopTestFinal.modules.order.repository.OrderRepository;
import com.btg.PetShopTestFinal.modules.order.usecase.FindOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class FindOrderUnitTest {
    @InjectMocks
    private FindOrder findOrder;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllOrders() {

        List<Order> mockOrders = Arrays.asList(new Order());
        when(orderRepository.findAll()).thenReturn(mockOrders);

        List<OrderResponse> result = findOrder.findAll();

        assertEquals(mockOrders.size(), result.size());

    }

    @Test
    public void testFindOrderByIdSuccessfully() throws Exception {

        String orderId = "123";
        Order mockOrder = new Order();
        when(orderRepository.findOrderById(orderId)).thenReturn(mockOrder);

        OrderResponse result = findOrder.findById(orderId);

        assertNotNull(result);

    }

    @Test
    public void testFindOrderByIdNotFound() {

        String orderId = "456";
        when(orderRepository.findOrderById(orderId)).thenReturn(null);

        assertThrows(Exception.class, () -> findOrder.findById(orderId));

    }
}
