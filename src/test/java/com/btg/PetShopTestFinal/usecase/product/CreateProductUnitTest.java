package com.btg.PetShopTestFinal.usecase.product;
import com.btg.PetShopTestFinal.modules.product.dto.ProductRequest;
import com.btg.PetShopTestFinal.modules.product.dto.ProductResponse;
import com.btg.PetShopTestFinal.modules.product.entity.Product;
import com.btg.PetShopTestFinal.modules.product.repository.ProductRepository;
import com.btg.PetShopTestFinal.modules.product.usecase.CreateProduct;
import jakarta.validation.constraints.AssertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateProductUnitTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private CreateProduct createProduct;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testProductCreation() {
        ProductRequest request = new ProductRequest();
        request.setName("Product");
        request.setQuantityStock(10);
        request.setPrice(BigDecimal.valueOf(20.0));

        Product savedProduct = new Product();
        savedProduct.setId(1);
        savedProduct.setName(request.getName());
        savedProduct.setQuantityStock(request.getQuantityStock());
        savedProduct.setPrice(request.getPrice());

        when(repository.save(any(Product.class))).thenReturn(savedProduct);

        try {
            ProductResponse response = createProduct.execute(request);
            assertNotNull(response);
            //assertEquals(savedProduct.getId(), response.getId());
            assertEquals(savedProduct.getName(), response.getName());
           // assertEquals(savedProduct.getQuantityStock(), response.getQuantityStock());
            assertEquals(savedProduct.getPrice(), response.getPrice());
        } catch (Exception e) {
            fail("Exception not expected: " + e.getMessage());
        }

        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    void testInvalidProductCreation() {
        ProductRequest request = new ProductRequest();

        request.setQuantityStock(10);
        request.setPrice(BigDecimal.valueOf(20.0));

        assertThrows(Exception.class, () -> createProduct.execute(request));

        request.setName("Product");
        request.setQuantityStock(null);

        assertThrows(Exception.class, () -> createProduct.execute(request));


        request.setQuantityStock(10);
        request.setPrice(null);

        assertThrows(Exception.class, () -> createProduct.execute(request));
    }
}
