package com.btg.PetShopTestFinal.usecase.orderItem;

import com.btg.PetShopTestFinal.infra.exception.ClientBadRequest;
import com.btg.PetShopTestFinal.modules.order.entity.Order;
import com.btg.PetShopTestFinal.modules.orderItem.entity.OrderItem;
import com.btg.PetShopTestFinal.modules.orderItem.repository.OrderItemRepository;
import com.btg.PetShopTestFinal.modules.orderItem.usecase.DeleteOrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DeleteOrderItemUnitTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private DeleteOrderItem deleteOrderItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testDeleteOrderItem() {
        String orderId = "1";
        OrderItem orderItem = new OrderItem();
        orderItem.setId(Integer.valueOf(orderId));

        when(orderItemRepository.findOrderItemById(orderId)).thenReturn(orderItem);

        try {
            deleteOrderItem.execute(orderId);
            verify(orderItemRepository, times(1)).delete(orderItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExecuteOrderSuccessfully() throws Exception {

        String orderId = "123";
        Order mockOrder = new Order();
        OrderItem OrderItem = new OrderItem();
        when(orderItemRepository.findOrderItemById(orderId)).thenReturn(OrderItem);

        deleteOrderItem.execute(orderId);

        verify(orderItemRepository, times(1)).delete(OrderItem);
    }


    @Test
    void testDeleteNonExistingOrderItem() {
        String orderId = "non-existing-id";

        when(orderItemRepository.findOrderItemById(orderId)).thenReturn(null);

        assertThrows(Exception.class, () -> deleteOrderItem.execute(orderId));
    }

    @Test
    public void testExecuteOrderNotFound() {

        String orderId = "456";
        when(orderItemRepository.findOrderItemById(orderId)).thenReturn(null);

        assertThrows(Exception.class, () -> deleteOrderItem.execute(orderId));

        verify(orderItemRepository, never()).delete(any(OrderItem.class));
    }
}
