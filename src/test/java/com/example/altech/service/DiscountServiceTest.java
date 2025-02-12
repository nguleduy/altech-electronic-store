package com.example.altech.service;

import com.example.altech.dto.DiscountDTO;
import com.example.altech.exception.ResourceNotFoundException;
import com.example.altech.model.Discount;
import com.example.altech.model.Product;
import com.example.altech.model.Promotion;
import com.example.altech.repository.DiscountRepository;
import com.example.altech.repository.ProductRepository;
import com.example.altech.repository.PromotionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = DiscountService.class)
class DiscountServiceTest {

    @MockBean
    private DiscountRepository discountRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private PromotionRepository promotionRepository;

    @Autowired
    private DiscountService discountService;

    @Test
    void testAddDiscount() {
        Product product = new Product(1L, "Laptop", new BigDecimal("1000.00"));
        Promotion promotion = new Promotion(1L, "BUY_1_GET_50_OFF_SECOND", "Buy 1 Get 50% Off Second");
        Discount discount = new Discount(1L, product, promotion);

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(discountRepository.save(Mockito.any(Discount.class))).thenReturn(discount);
        Mockito.when(promotionRepository.findByType("BUY_1_GET_50_OFF_SECOND")).thenReturn(List.of(promotion));

        DiscountDTO savedDiscount = discountService.addDiscount(1L, "BUY_1_GET_50_OFF_SECOND");

        assertNotNull(savedDiscount);
        assertEquals("BUY_1_GET_50_OFF_SECOND", promotion.getType());
    }

    @Test
    void testAddDiscount_ProductNotFound() {
        Mockito.when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            discountService.addDiscount(99L, "BUY_1_GET_50_OFF_SECOND");
        });
    }
}
