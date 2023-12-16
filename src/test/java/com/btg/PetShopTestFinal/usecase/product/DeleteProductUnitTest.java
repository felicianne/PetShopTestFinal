package com.btg.PetShopTestFinal.usecase.product;

import com.btg.PetShopTestFinal.modules.product.entity.Product;
import com.btg.PetShopTestFinal.modules.product.repository.ProductRepository;
import com.btg.PetShopTestFinal.modules.product.usecase.DeleteProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeleteProductUnitTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private DeleteProduct deleteProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testDeleteProduct() {
        String productId = "1";
        Product product = new Product();
        product.setId(Integer.valueOf(productId));

        when(repository.findProductById(productId)).thenReturn(product);

        try {
            deleteProduct.execute(productId);
            verify(repository, times(1)).delete(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDeleteNonExistingProduct() {
        String productId = "non-existing-id";

        when(repository.findProductById(productId)).thenReturn(null);

        assertThrows(Exception.class, () -> deleteProduct.execute(productId));
    }
}
