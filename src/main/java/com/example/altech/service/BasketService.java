package com.example.altech.service;

import com.example.altech.constant.DiscountType;
import com.example.altech.dto.BasketDTO;
import com.example.altech.dto.ReceiptDTO;
import com.example.altech.dto.ReceiptItemDTO;
import com.example.altech.exception.InvalidDataException;
import com.example.altech.exception.ResourceNotFoundException;
import com.example.altech.mapper.BasketMapper;
import com.example.altech.model.Basket;
import com.example.altech.model.Discount;
import com.example.altech.model.Product;
import com.example.altech.repository.BasketRepository;
import com.example.altech.repository.DiscountRepository;
import com.example.altech.repository.ProductRepository;
import com.example.altech.util.DiscountCalculator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Basket Service.
 */
@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;
    private static final Logger logger = LoggerFactory.getLogger(BasketService.class);

    /**
     * Adds an item to the basket.
     *
     * @param customerId
     * @param productId
     * @param quantity
     * @return
     */
    @Transactional
    public BasketDTO addToBasket(Long customerId, Long productId, int quantity) {
        if (quantity <= 0) {
            logger.error("Quantity must be greater than zero.");
            throw new InvalidDataException("Quantity must be greater than zero.");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Optional<Basket> existingBasket =
                basketRepository.findByCustomerIdAndProductId(customerId, productId);

        Basket response = new Basket();
        if (existingBasket.isPresent()) {
            response = existingBasket.get();
            response.setQuantity(response.getQuantity() + quantity);
            basketRepository.save(response);
        } else {
            response.setCustomerId(customerId);
            response.setProduct(product);
            response.setQuantity(quantity);
            basketRepository.save(response);
        }
        logger.info("Added item to basket.");
        return BasketMapper.toDto(response);
    }

    /**
     * Removes an item from the basket.
     *
     * @param customerId
     * @param productId
     * @return
     */
    @Transactional
    public BasketDTO removeProductFromBasket(Long customerId, Long productId, int quantity) {
        if (quantity <= 0) {
            logger.error("Quantity must be greater than zero.");
            throw new InvalidDataException("Quantity must be greater than zero.");
        }

        Basket basket = basketRepository.findByCustomerIdAndProductId(customerId, productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not in basket"));

        Basket response = new Basket();
        if (basket.getQuantity() > quantity) {
            basket.setQuantity(basket.getQuantity() - quantity);
            basketRepository.save(basket);
            response = basket;
        } else {
            if (basket.getQuantity() < quantity) {
                logger.error("Quantity must be less than existing quantity.");
                throw new InvalidDataException(
                        "Quantity must be less than or equal existing quantity.");
            }
            basketRepository.deleteByCustomerIdAndProductId(customerId, productId);
            response.setProduct(new Product());
        }
        logger.info("Removed item from basket.");
        return BasketMapper.toDto(response);
    }

    /**
     * Calculates the receipt for a customer.
     *
     * @param customerId
     * @return ReceiptDTO
     */
    @Transactional
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

            BigDecimal discountAmount = BigDecimal.ZERO;
            for (Discount discount : discounts) {
                if (discount.getPromotion().getType()
                        .equals(DiscountType.BUY_ONE_GET_HALF_OFF.value())) {
                    discountAmount = DiscountCalculator.applyBuyOneGetHalfOff(
                            amount, item.getProduct().getPrice(),
                            item.getQuantity());
                }
            }

            receiptItems.add(
                    new ReceiptItemDTO(
                            item.getProduct().getName(),
                            item.getQuantity(), amount, discountAmount));
            total = total.add(
                    discountAmount.compareTo(BigDecimal.ZERO) > 0 ? discountAmount : amount);
        }

        logger.info("Receipt items: {}", receiptItems);
        return new ReceiptDTO(customerId, receiptItems, total);
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