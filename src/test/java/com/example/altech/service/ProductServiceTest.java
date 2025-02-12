package com.example.altech.service;

import com.example.altech.dto.CreateProductDTO;
import com.example.altech.dto.ProductDTO;
import com.example.altech.model.Product;
import com.example.altech.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ProductService.class)
class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Test
    void testCreateProduct() {
        CreateProductDTO requestDTO = CreateProductDTO.builder()
                .name("Laptop")
                .price(new BigDecimal("1000.00")).build();

        Product product = Product.builder()
                .name("Laptop")
                .price(new BigDecimal("1000.00")).build();
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        ProductDTO savedProduct = productService.createProduct(requestDTO);

        assertNotNull(savedProduct);
        assertEquals("Laptop", savedProduct.getName());
    }

    @Test
    void testRemoveProduct() {
        Long productId = 1L;
        Mockito.when(productRepository.existsById(productId)).thenReturn(true);
        productService.removeProduct(productId);
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(productId);
    }
}
