package com.example.altech.service;

import com.example.altech.dto.DiscountDTO;
import com.example.altech.exception.ResourceNotFoundException;
import com.example.altech.mapper.DiscountMapper;
import com.example.altech.model.Discount;
import com.example.altech.model.Product;
import com.example.altech.model.Promotion;
import com.example.altech.repository.DiscountRepository;
import com.example.altech.repository.ProductRepository;
import com.example.altech.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Discount Service.
 */
@Service
@RequiredArgsConstructor
public class DiscountService {
    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    /**
     * Adds a discount to a product.
     *
     * @param productId     The ID of the product.
     * @param promotionType The type of promotion.
     * @return The added discount.
     */
    public DiscountDTO addDiscount(Long productId, String promotionType) {
        List<Promotion> promotions = promotionRepository.findByType(promotionType);
        if (promotions.isEmpty()) {
            throw new ResourceNotFoundException("Promotion not found");
        }

        Optional<Discount> existingDiscount =
                discountRepository.findByProductIdAndPromotionId(
                        productId, promotions.get(0).getId());
        if (existingDiscount.isPresent()) {
            throw new ResourceNotFoundException("Discount already exists");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        DiscountDTO discountDTO = DiscountDTO.builder()
                .productId(product.getId())
                .promotionId(promotions.get(0).getId()).build();
        Discount discount = DiscountMapper.toEntity(discountDTO);
        discount.setProduct(product);
        return DiscountMapper.toDto(discountRepository.save(discount));
    }

    /**
     * Retrieves discounts for a product.
     *
     * @param productId The ID of the product.
     * @return A list of discounts for the product.
     */
    public List<DiscountDTO> getDiscount(Long productId) {
        List<Discount> discounts = discountRepository.findByProductId(productId);
        if (discounts.isEmpty()) {
            throw new ResourceNotFoundException("Discount not found");
        }
        return discounts.stream().map(DiscountMapper::toDto).toList();
    }
}

