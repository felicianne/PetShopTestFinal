package com.btg.PetShopTestFinal.infra.queue.dto;

import com.btg.PetShopTestFinal.modules.product.entity.Product;
import lombok.Data;
@Data
public class StockReservationRequest {
     private String skuId;
     private Product item;

}
