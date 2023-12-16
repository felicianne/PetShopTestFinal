package com.btg.PetShopTestFinal.modules.product.usecase;

import com.btg.PetShopTestFinal.modules.product.dto.ProductRequest;
import com.btg.PetShopTestFinal.modules.product.dto.ProductResponse;
import com.btg.PetShopTestFinal.modules.product.entity.Product;
import com.btg.PetShopTestFinal.modules.product.repository.ProductRepository;
import com.btg.PetShopTestFinal.utils.ProductConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateProduct {

    @Autowired
    ProductRepository repository;

    public ProductResponse execute(String id, ProductRequest productRequest) throws Exception {
        if (repository.findProductById(id) == null) {
            throw new Exception("Does not exist product");
        }

        Product product = ProductConvert.toEntity(productRequest);
        product.setSkuId(id);
        repository.save(product);
        return ProductConvert.toResponse(product);
    }




}
