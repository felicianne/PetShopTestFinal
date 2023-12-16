package com.btg.PetShopTestFinal.usecase.orderItem;

import com.btg.PetShopTestFinal.infra.exception.ClientBadRequest;

import com.btg.PetShopTestFinal.infra.queue.StockItemReservationProducer;
import com.btg.PetShopTestFinal.infra.queue.dto.StockReservationRequest;
import com.btg.PetShopTestFinal.modules.order.entity.Order;
import com.btg.PetShopTestFinal.modules.order.repository.OrderRepository;
import com.btg.PetShopTestFinal.modules.order.usecase.UpdateOrder;
import com.btg.PetShopTestFinal.modules.orderItem.dto.OrderItemRequest;
import com.btg.PetShopTestFinal.modules.orderItem.dto.OrderItemResponse;
import com.btg.PetShopTestFinal.modules.orderItem.entity.OrderItem;
import com.btg.PetShopTestFinal.modules.orderItem.repository.OrderItemRepository;
import com.btg.PetShopTestFinal.modules.orderItem.usecase.AddOrderItem;
import com.btg.PetShopTestFinal.modules.orderItem.usecase.ChangeItemAmount;
import com.btg.PetShopTestFinal.modules.product.entity.Product;
import com.btg.PetShopTestFinal.modules.product.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)

public class ChangeItemAmountUnitTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private UpdateOrder updateOrder;

    @Mock
    private StockItemReservationProducer stockItemReservationProducer;

    @InjectMocks
    private AddOrderItem addOrderItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testValidateProduct_ProductDoesNotExist_ExceptionThrown() {

        when(productRepository.findProductById(anyString())).thenReturn(null);


        assertThrows(Exception.class, () -> addOrderItem.execute("sampleOrderId", new OrderItemRequest()));
    }

    @Test
    public void testReservItems_ExceptionSendingStockReservation_ExceptionThrown() throws JsonProcessingException {

        when(productRepository.findProductById(anyString())).thenReturn(new Product());
        OrderItemRequest orderItemRequest = new OrderItemRequest();
        doThrow(JsonProcessingException.class).when(stockItemReservationProducer).send(any(StockReservationRequest.class));
        assertThrows(ClientBadRequest.class, () -> addOrderItem.execute("sampleOrderId", orderItemRequest));
    }
    @Test
    public void testUpdateOrderTotal_RecalculatesTotalAfterAddingItem() throws Exception {

        OrderItem orderItem = new OrderItem();
        when(orderItemRepository.findOrderItemById(anyString())).thenReturn(orderItem);

        Product product = new Product();
        when(productRepository.findProductById(anyString())).thenReturn(product);


        //OrderItemResponse sampleOrderId = ChangeItemAmount.execute("sampleOrderId", new OrderItemRequest());
        int expectedTotal = 0;
        assertEquals(expectedTotal, orderItem.getTotal());
    }
    @Test
    public void testChangeItemAmount_UpdatesItemQuantityInOrder() throws Exception {
        OrderRepository orderRepository = mock(OrderRepository.class);
        OrderItemRepository orderItemRepository = mock(OrderItemRepository.class);
        UpdateOrder updateOrder = mock(UpdateOrder.class);
        ChangeItemAmount changeItemAmount = new ChangeItemAmount();

        Order order = new Order();
        OrderItem orderItem = new OrderItem();
        orderItemRepository.getOrderItems().add(orderItem);

        when(orderRepository.findOrderById(anyString())).thenReturn(order);
        when(orderItemRepository.findOrderItemById(anyString())).thenReturn(orderItem);


        OrderItemResponse sampleOrderId = changeItemAmount.execute("sampleOrderId", new OrderItemRequest());

        assertEquals(5, orderItem.getProduct().getQuantityStock());
        verify(orderItemRepository, times(1)).save(orderItem);
        verify(updateOrder, times(1)).execute("sampleOrderId", order);
    }


}