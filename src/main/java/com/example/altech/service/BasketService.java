package com.example.altech.service;

import com.example.altech.constant.DiscountType;
import com.example.altech.dto.BasketDTO;
import com.example.altech.dto.ReceiptDTO;
import com.example.altech.dto.ReceiptItemDTO;
import com.example.altech.exception.ResourceNotFoundException;
import com.example.altech.mapper.BasketMapper;
import com.example.altech.model.Basket;
import com.example.altech.model.Customer;
import com.example.altech.model.Discount;
import com.example.altech.model.Product;
import com.example.altech.repository.BasketRepository;
import com.example.altech.repository.CustomerRepository;
import com.example.altech.repository.DiscountRepository;
import com.example.altech.repository.ProductRepository;
import com.example.altech.util.DiscountCalculator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Basket Service.
 */
@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;
    private final CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(BasketService.class);

    /**
     * Adds an item to the basket.
     *
     * @param customerId
     * @param productId
     * @param quantity
     * @return
     */
    public BasketDTO addToBasket(Long customerId, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Basket basket = Basket.builder()
                .customerId(customer.getId())
                .product(product)
                .quantity(quantity).build();
        basket = basketRepository.save(basket);
        logger.info("Added item to basket: {}", basket);
        return BasketMapper.toDto(basketRepository.save(basket));
    }

    /**
     * Removes an item from the basket.
     *
     * @param basketId
     */
    public void removeFromBasket(Long basketId) {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new ResourceNotFoundException("Basket not found"));
        basketRepository.deleteById(basket.getId());
        logger.info("Removed item from basket: {}", basket);
    }

    /**
     * Calculates the receipt for a customer.
     *
     * @param customerId
     * @return ReceiptDTO
     */
    public ReceiptDTO calculateReceipt(Long customerId) {
        List<Basket> basketItems = basketRepository.findByCustomerId(customerId);
        logger.info("Basket items: {}", basketItems);

        BigDecimal total = BigDecimal.ZERO;
        List<ReceiptItemDTO> receiptItems = new ArrayList<>();

        for (Basket item : basketItems) {
            BigDecimal amount =
                    item.getProduct().getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()));
            List<Discount> discounts =
                    discountRepository.findByProductId(item.getProduct().getId());
            logger.info("Discounts: {}", discounts);

            for (Discount discount : discounts) {
                if (discount.getPromotion().getType()
                        .equals(DiscountType.BUY_ONE_GET_HALF_OFF.value())) {
                    amount = DiscountCalculator.applyBuyOneGetHalfOff(
                            amount, item.getProduct().getPrice(),
                            item.getQuantity());
                }
            }

            receiptItems.add(
                    new ReceiptItemDTO(
                            item.getProduct().getName(),
                            item.getQuantity(), amount));
            total = total.add(amount);
        }

        logger.info("Receipt items: {}", receiptItems);
        return new ReceiptDTO(receiptItems, total);
    }

    /**
     * Retrieves the basket for a customer.
     *
     * @param customerId
     * @return List BasketDTO
     */
    public List<BasketDTO> getBasket(Long customerId) {
        List<Basket> basketItems = basketRepository.findByCustomerId(customerId);
        logger.info("Baskets: {}", basketItems);
        return basketItems.stream().map(BasketMapper::toDto).toList();
    }
}