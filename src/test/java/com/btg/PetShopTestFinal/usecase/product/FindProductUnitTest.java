package com.btg.PetShopTestFinal.usecase.product;

import com.btg.PetShopTestFinal.modules.product.dto.ProductResponse;
import com.btg.PetShopTestFinal.modules.product.entity.Product;
import com.btg.PetShopTestFinal.modules.product.repository.ProductRepository;
import com.btg.PetShopTestFinal.modules.product.usecase.FindProduct;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class FindProductUnitTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private FindProduct findProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testListAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());

        when(repository.findAll()).thenReturn(productList);

        List<ProductResponse> result = findProduct.listAll();
        assertEquals(productList.size(), result.size());
    }

    @Test
    void testFindProductById() {
        String productId = "1";
        Product product = new Product();
        product.setId(Integer.valueOf(productId));

        when(repository.findProductById(productId)).thenReturn(product);

        ProductResponse result = findProduct.findById(productId);
        //Assertions.assertEquals(productId, result.getId());
    }

    @Test
    void testFindNonExistingProductById() {
        String nonExistingId = "non-existing-id";

        when(repository.findProductById(nonExistingId)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> findProduct.findById(nonExistingId));
    }
}
