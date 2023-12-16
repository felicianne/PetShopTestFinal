package com.btg.PetShopTestFinal.modules.orderItem.usecase;

import com.btg.PetShopTestFinal.infra.exception.ClientBadRequest;
import com.btg.PetShopTestFinal.infra.queue.StockItemReservationProducer;
import com.btg.PetShopTestFinal.infra.queue.dto.StockReservationRequest;
import com.btg.PetShopTestFinal.modules.order.entity.Order;
import com.btg.PetShopTestFinal.modules.order.repository.OrderRepository;
import com.btg.PetShopTestFinal.modules.order.usecase.UpdateOrder;
import com.btg.PetShopTestFinal.modules.order.utils.CalculateTotal;
import com.btg.PetShopTestFinal.modules.orderItem.dto.OrderItemRequest;
import com.btg.PetShopTestFinal.modules.orderItem.dto.OrderItemResponse;
import com.btg.PetShopTestFinal.modules.orderItem.entity.OrderItem;
import com.btg.PetShopTestFinal.modules.orderItem.repository.OrderItemRepository;
import com.btg.PetShopTestFinal.modules.product.entity.Product;
import com.btg.PetShopTestFinal.modules.product.repository.ProductRepository;
import com.btg.PetShopTestFinal.utils.OrderItemConvert;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AddOrderItem {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    UpdateOrder updateOrder;
    @Autowired
    StockItemReservationProducer StockItemReservation;

    public OrderItemResponse execute(String orderId, OrderItemRequest orderItemRequest) throws Exception {
        Order order = validateOrder(orderId);

        Product product = validateProduct(orderItemRequest.getProductId());

        OrderItem orderItem = saveOrderItem(order, orderItemRequest, product);

        updateOrderTotal(order);
        updateOrder.execute(orderId, order);

        return OrderItemConvert.toResponseOrderItem(orderItem);
    }

    private Order validateOrder(String orderID) throws ClientBadRequest {
        Order order = orderRepository.findOrderById(orderID);
        if (order == null) {
            throw new ClientBadRequest("Order not found");
        }
        return order;
    }

    private Product validateProduct(String productSku) throws Exception {
        Product product = productRepository.findProductById(productSku);
        if (product == null) {
            throw new Exception("Product not found");
        }
        return product;
    }


    private OrderItem saveOrderItem(Order order, OrderItemRequest orderItemRequest, Product product) {
        OrderItem newItem = OrderItemConvert.toEntity(orderItemRequest, order, product);
        orderItemRepository.save(newItem);
        order.getOrderItens().add(newItem);
        return newItem;
    }

    private void updateOrderTotal(Order order) {
        order.setTotal(CalculateTotal.execute(order));
        orderRepository.save(order);
    }
}