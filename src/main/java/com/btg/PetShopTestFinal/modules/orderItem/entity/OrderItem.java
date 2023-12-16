package com.btg.PetShopTestFinal.modules.orderItem.entity;


import com.btg.PetShopTestFinal.modules.order.entity.Order;
import com.btg.PetShopTestFinal.modules.product.entity.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Entity(name = "OrderItem")
@Table(name = "Order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "id_transaction")
    private String idTransaction;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private BigDecimal total;

    public double getQuantity() {
        return 0;
    }
}
