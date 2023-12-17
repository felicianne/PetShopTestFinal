package com.btg.PetShopTestFinal.usecase.order;

import com.btg.PetShopTestFinal.modules.order.dto.OrderResponse;
import com.btg.PetShopTestFinal.modules.order.entity.Order;
import com.btg.PetShopTestFinal.modules.order.entity.OrderStatus;
import com.btg.PetShopTestFinal.modules.order.repository.OrderRepository;
import com.btg.PetShopTestFinal.modules.order.usecase.UpdateOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UpdateOrderUnitTest {

    @InjectMocks
    private UpdateOrder updateOrder;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecuteOrderSuccessfully() throws Exception {

        String orderId = "123";
        String orderItem = "12.52";
        Order mockExistingOrder = new Order();
       // mockExistingOrder.setTotal(BigDecimal.TEN);
        Order mockUpdatedOrderInput = new Order();
      //  mockUpdatedOrderInput.setTotal(BigDecimal.TEN);


        when(orderRepository.findOrderById(orderId)).thenReturn(mockExistingOrder);

        OrderResponse result = updateOrder.execute(orderId, mockUpdatedOrderInput);

        assertNotNull(result);
        assertEquals(mockUpdatedOrderInput.getStatus(), mockExistingOrder.getStatus());
        assertEquals(mockUpdatedOrderInput.getOrderItens(), mockExistingOrder.getOrderItens());
        assertEquals(mockUpdatedOrderInput.getTotal(), mockExistingOrder.getTotal());
        verify(orderRepository, times(1)).save(mockExistingOrder);
    }

    @Test
    public void testExecuteOrderNotFound() {

        String orderId = "456";
        when(orderRepository.findOrderById(orderId)).thenReturn(null);

        assertThrows(Exception.class, () -> updateOrder.execute(orderId, new Order()));
        verify(orderRepository, never()).save(any(Order.class));
    }
}
