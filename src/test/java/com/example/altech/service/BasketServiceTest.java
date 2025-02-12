package com.example.altech.service;

import com.example.altech.dto.BasketDTO;
import com.example.altech.dto.ReceiptDTO;
import com.example.altech.model.Basket;
import com.example.altech.model.Customer;
import com.example.altech.model.Discount;
import com.example.altech.model.Product;
import com.example.altech.model.Promotion;
import com.example.altech.repository.BasketRepository;
import com.example.altech.repository.CustomerRepository;
import com.example.altech.repository.DiscountRepository;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = BasketService.class)
class BasketServiceTest {

    @MockBean
    private BasketRepository basketRepository;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private DiscountRepository discountRepository;
    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private BasketService basketService;

    @Test
    void testAddToBasket() {
        Product product = new Product(1L, "Laptop", new BigDecimal("1000.00"));
        Basket basket = new Basket(null, 123L, product, 2);
        Customer customer = new Customer(123L, "John", "123-456-7890", "123 Main St");

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(basketRepository.save(Mockito.any(Basket.class))).thenReturn(basket);
        Mockito.when(customerRepository.findById(123L)).thenReturn(Optional.of(customer));

        BasketDTO savedBasket = basketService.addToBasket(123L, 1L, 2);

        assertNotNull(savedBasket);
        assertEquals(123L, savedBasket.getCustomerId());
        assertEquals(2, savedBasket.getQuantity());
    }

    @Test
    void testCalculateReceipt() {
        Product product = new Product(1L, "Laptop", new BigDecimal("1000.00"));
        Promotion promotion = new Promotion(1L, "BUY_1_GET_50_OFF_SECOND", "Buy 1 Get 50% Off Second");
        Basket basket = new Basket(1L, 123L, product, 2);
        Discount discount = new Discount(1L, product, promotion);

        List<Basket> basketItems = Collections.singletonList(basket);
        List<Discount> discounts = Collections.singletonList(discount);

        Mockito.when(basketRepository.findByCustomerId(123L)).thenReturn(basketItems);
        Mockito.when(discountRepository.findByProductId(1L)).thenReturn(discounts);

        ReceiptDTO receipt = basketService.calculateReceipt(123L);

        assertNotNull(receipt);
        assertEquals(1, receipt.getReceiptItems().size());
        assertEquals(new BigDecimal("2000.00"), receipt.getTotalPrice());
    }
}
