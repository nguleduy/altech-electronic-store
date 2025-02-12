package com.example.altech.mapper;

import com.example.altech.dto.BasketDTO;
import com.example.altech.model.Basket;

/**
 * Basket mapper.
 */
public class BasketMapper {

    private BasketMapper() {
    }

    /**
     * Converts a Basket entity to a BasketDTO.
     *
     * @param basket The Basket entity to convert.
     * @return The converted BasketDTO.
     */
    public static BasketDTO toDto(Basket basket) {
        return BasketDTO.builder()
                .id(basket.getId())
                .customerId(basket.getCustomerId())
                .productId(basket.getProduct().getId())
                .quantity(basket.getQuantity())
                .build();
    }
}
