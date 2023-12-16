package com.btg.PetShopTestFinal.usecase.product;

import com.btg.PetShopTestFinal.modules.product.dto.ProductRequest;
import com.btg.PetShopTestFinal.modules.product.dto.ProductResponse;
import com.btg.PetShopTestFinal.modules.product.entity.Product;
import com.btg.PetShopTestFinal.modules.product.repository.ProductRepository;
import com.btg.PetShopTestFinal.modules.product.usecase.UpdateProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UpdateProductUnitTest {


    @Mock
    private ProductRepository repository;

    @InjectMocks
    private UpdateProduct updateProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateProduct() {
        String productId = "id";
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("New Product");
        productRequest.setQuantityStock(10);
        productRequest.setPrice(BigDecimal.valueOf(25.0));

        Product existingProduct = new Product();
        existingProduct.setId(Integer.valueOf(productId));
        existingProduct.setName("Old Product");
        existingProduct.setQuantityStock(5);
        existingProduct.setPrice(BigDecimal.valueOf(20.0));

        when(repository.findProductById(productId)).thenReturn(existingProduct);
        when(repository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        try {
            ProductResponse result = updateProduct.execute(productId, productRequest);
            assertEquals(productId, result.getSkuId());
            assertEquals(productRequest.getName(), result.getName());
            assertEquals(productRequest.getQuantityStock(), result.getQuantityStock());
            assertEquals(productRequest.getPrice(), result.getPrice());


            verify(repository, times(1)).save(existingProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testUpdateNonExistingProduct() {
        String nonExistingProductId = "non-existing-id";
        ProductRequest productRequest = new ProductRequest();

        when(repository.findProductById(nonExistingProductId)).thenReturn(null);

        assertThrows(Exception.class, () -> updateProduct.execute(nonExistingProductId, productRequest));
    }
}
