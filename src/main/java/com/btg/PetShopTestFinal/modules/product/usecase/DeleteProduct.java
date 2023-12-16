package com.btg.PetShopTestFinal.modules.product.usecase;

import com.btg.PetShopTestFinal.modules.product.entity.Product;
import com.btg.PetShopTestFinal.modules.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteProduct {
    @Autowired
    ProductRepository repository;

    public void execute(String id) throws Exception {
        Product product = repository.findProductById(id);

        if (product == null){
            throw new Exception("Product not found ");
        }

        repository.delete(product);
    }
}