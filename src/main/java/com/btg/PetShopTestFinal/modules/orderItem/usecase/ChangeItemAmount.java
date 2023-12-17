package com.btg.PetShopTestFinal.modules.orderItem.usecase;

import com.btg.PetShopTestFinal.infra.exception.ClientBadRequest;
import com.btg.PetShopTestFinal.modules.order.entity.Order;
import com.btg.PetShopTestFinal.modules.order.repository.OrderRepository;
import com.btg.PetShopTestFinal.modules.order.utils.CalculateTotal;
import com.btg.PetShopTestFinal.modules.orderItem.dto.OrderItemRequest;
import com.btg.PetShopTestFinal.modules.orderItem.dto.OrderItemResponse;
import com.btg.PetShopTestFinal.modules.orderItem.entity.OrderItem;
import com.btg.PetShopTestFinal.modules.orderItem.repository.OrderItemRepository;
import com.btg.PetShopTestFinal.modules.product.entity.Product;
import com.btg.PetShopTestFinal.modules.product.repository.ProductRepository;
import com.btg.PetShopTestFinal.utils.OrderItemConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class
ChangeItemAmount {
    @Autowired
    static OrderItemRepository orderItemRepository;
    @Autowired
    static ProductRepository productRepository;
    @Autowired
    static OrderRepository orderRepository;
    @Autowired
    DeleteOrderItem deleteOrderItem;

    public static OrderItemResponse execute(String orderItemID, OrderItemRequest orderItemRequest) throws Exception {
        OrderItem orderItem = validateOrderItem(orderItemID);

        if (!deleteOrderAmountEqualsZero(orderItemRequest)) {
            deleteOrderItem(orderItem);
        }

        Product product = validateProduct(orderItemRequest.getProductId());

        updateOrderItem(orderItem, orderItemRequest, product);
        updateOrderTotal(orderItem.getOrder());

        return OrderItemConvert.toResponseOrderItem(orderItem);
    }

    private static OrderItem validateOrderItem(String orderItemID) throws ClientBadRequest {
        OrderItem orderItem = orderItemRepository.findOrderItemById(orderItemID);
        if (orderItem == null) {
            throw new ClientBadRequest("Order not found");
        }
        return orderItem;
    }

    private static boolean deleteOrderAmountEqualsZero(OrderItemRequest orderItemRequest){
        return orderItemRequest.getAmount() != 0;
    }

    private static void deleteOrderItem(OrderItem orderItem) {
        orderItemRepository.delete(orderItem);
    }

    private static Product validateProduct(String productSku) throws Exception {
        Product product = productRepository.findProductById(productSku);
        if (product == null) {
            throw new Exception("Product not found");
        }
        return product;
    }

    private static void updateOrderItem(OrderItem orderItem, OrderItemRequest orderItemRequest, Product product) {
        orderItem.setAmount(orderItemRequest.getAmount());
        orderItem.setTotal(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getAmount())));
        orderItemRepository.save(orderItem);
    }

    private static void updateOrderTotal(Order order) {
        order.setTotal(CalculateTotal.execute(order));
        orderRepository.save(order);
    }
}
