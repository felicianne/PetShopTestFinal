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
    OrderItemRepository ordemItemRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    DeleteOrderItem deleteOrderItem;

    public OrderItemResponse execute(String orderItemID, OrderItemRequest orderItemRequest) throws Exception {
        OrderItem orderItem = validateOrderItem(orderItemID);

        if (!deleteOrderAmountEqualsZero(orderItemRequest)) {
            deleteOrderItem(orderItem);
        }

        Product product = validateProduct(orderItemRequest.getProductId());

        updateOrderItem(orderItem, orderItemRequest, product);
        updateOrderTotal(orderItem.getOrder());

        return OrderItemConvert.toResponseOrderItem(orderItem);
    }

    private OrderItem validateOrderItem(String orderItemID) throws ClientBadRequest {
        OrderItem orderItem = ordemItemRepository.findOrderItemById(orderItemID);
        if (orderItem == null) {
            throw new ClientBadRequest("Order not found");
        }
        return orderItem;
    }

    private boolean deleteOrderAmountEqualsZero(OrderItemRequest orderItemRequest){
        return orderItemRequest.getAmount() != 0;
    }

    private void deleteOrderItem(OrderItem orderItem) {
        ordemItemRepository.delete(orderItem);
    }

    private Product validateProduct(String productSku) throws Exception {
        Product product = productRepository.findProductById(productSku);
        if (product == null) {
            throw new Exception("Product not found");
        }
        return product;
    }

    private void updateOrderItem(OrderItem orderItem, OrderItemRequest orderItemRequest, Product product) {
        orderItem.setAmount(orderItemRequest.getAmount());
        orderItem.setTotal(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getAmount())));
        ordemItemRepository.save(orderItem);
    }

    private void updateOrderTotal(Order order) {
        order.setTotal(CalculateTotal.execute(order));
        orderRepository.save(order);
    }
}
