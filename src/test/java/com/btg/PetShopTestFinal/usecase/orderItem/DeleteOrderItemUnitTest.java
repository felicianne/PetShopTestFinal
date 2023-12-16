package com.btg.PetShopTestFinal.usecase.orderItem;

import com.btg.PetShopTestFinal.modules.orderItem.entity.OrderItem;
import com.btg.PetShopTestFinal.modules.orderItem.repository.OrderItemRepository;
import com.btg.PetShopTestFinal.modules.orderItem.usecase.DeleteOrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void testDeleteNonExistingOrderItem() {
        String orderId = "non-existing-id";

        when(orderItemRepository.findOrderItemById(orderId)).thenReturn(null);

        assertThrows(Exception.class, () -> deleteOrderItem.execute(orderId));
    }
}
