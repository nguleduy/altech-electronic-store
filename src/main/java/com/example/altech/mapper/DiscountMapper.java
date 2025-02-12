package com.example.altech.mapper;

import com.example.altech.dto.DiscountDTO;
import com.example.altech.model.Discount;
import com.example.altech.model.Product;
import com.example.altech.model.Promotion;

/**
 * Discount Mapper.
 */
public class DiscountMapper {

    private DiscountMapper() {
    }

    /**
     * Converts a discount entity to a discount DTO.
     *
     * @param discount The discount entity.
     * @return The discount DTO.
     */
    public static DiscountDTO toDto(Discount discount) {
        return DiscountDTO.builder()
                .id(discount.getId())
                .productId(discount.getProduct().getId())
                .promotionId(discount.getPromotion().getId()).build();
    }

    /**
     * Converts a discount DTO to a discount entity.
     *
     * @param dto The discount DTO.
     * @return The discount entity.
     */
    public static Discount toEntity(DiscountDTO dto) {
        return Discount.builder()
                .product(Product.builder().id(dto.getProductId()).build())
                .promotion(Promotion.builder().id(dto.getPromotionId()).build()).build();
    }
}

