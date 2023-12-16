package com.btg.PetShopTestFinal.usecase.orderItem;

import com.btg.PetShopTestFinal.infra.exception.ClientBadRequest;

import com.btg.PetShopTestFinal.infra.queue.StockItemReservationProducer;
import com.btg.PetShopTestFinal.modules.order.repository.OrderRepository;
import com.btg.PetShopTestFinal.modules.order.usecase.UpdateOrder;
import com.btg.PetShopTestFinal.modules.orderItem.dto.OrderItemRequest;

import com.btg.PetShopTestFinal.modules.orderItem.dto.OrderItemResponse;

import com.btg.PetShopTestFinal.modules.orderItem.repository.OrderItemRepository;
import com.btg.PetShopTestFinal.modules.orderItem.usecase.AddOrderItem;
import com.btg.PetShopTestFinal.modules.product.entity.Product;
import com.btg.PetShopTestFinal.modules.product.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AddOrderItemUnitTest {

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
    public void testExecute_AddsItemToExistingOrder() throws Exception {

        OrderItemRequest orderItemRequest = new OrderItemRequest();
        when(orderRepository.findOrderById(anyString())).thenReturn(null);
        Product product = new Product();
        when(productRepository.findProductById(orderItemRequest.getProductId())).thenReturn(product);
        assertThrows(ClientBadRequest.class, () -> addOrderItem.execute("invalidOrderId", orderItemRequest));


        //OrderItemResponse response = addOrderItem.execute("1", orderItemRequest);
        //StockReservationRequest stockReservationRequest = new StockReservationRequest();
        //when(stockItemReservationProducer.send(any(StockReservationRequest.class))).thenReturn(true);
        //assertNotNull(response);

    }

    @Test
    public void testExecute_InvalidOrder_ExceptionThrown() {

        when(orderRepository.findOrderById(anyString())).thenReturn(null);

        OrderItemRequest orderItemRequest = new OrderItemRequest();

        assertThrows(ClientBadRequest.class, () -> addOrderItem.execute("invalidOrderId", orderItemRequest));
    }


/*
    @Test
    public void testSaveOrderItem_SavesNewItemAndAddsToOrder() {

        Order order = new Order();
        when(orderRepository.findOrderById(anyString())).thenReturn(order);

        Product product = new Product();
        when(productRepository.findProductById(anyString())).thenReturn(product);


        OrderItemResponse response = addOrderItem.execute("sampleOrderId", new OrderItemRequest());


        assertEquals(1, order.getOrderItens().size());
        assertNotNull(response);

    }

 */
}