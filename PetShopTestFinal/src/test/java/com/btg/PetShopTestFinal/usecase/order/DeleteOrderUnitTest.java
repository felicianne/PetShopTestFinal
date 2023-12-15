package com.btg.PetShopTestFinal.usecase.order;

import com.btg.PetShopTestFinal.modules.order.entity.Order;
import com.btg.PetShopTestFinal.modules.order.repository.OrderRepository;
import com.btg.PetShopTestFinal.modules.order.usecase.DeleteOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class DeleteOrderUnitTest {
    @InjectMocks
    private DeleteOrder deleteOrder;

    @Mock
      private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        }
        @Test
        public void testExecuteOrderSuccessfully() throws Exception {

            String orderId = "123";
            Order mockOrder = new Order();
            when(orderRepository.findOrderById(orderId)).thenReturn(mockOrder);

            deleteOrder.execute(orderId);

            verify(orderRepository, times(1)).delete(mockOrder);
        }

        @Test
        public void testExecuteOrderNotFound() {

            String orderId = "456";
            when(orderRepository.findOrderById(orderId)).thenReturn(null);

            assertThrows(Exception.class, () -> deleteOrder.execute(orderId));

            verify(orderRepository, never()).delete(any(Order.class));
        }


    }


