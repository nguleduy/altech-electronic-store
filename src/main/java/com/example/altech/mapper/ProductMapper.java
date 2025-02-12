package com.example.altech.mapper;

import com.example.altech.dto.ProductDTO;
import com.example.altech.model.Product;

import java.util.List;

/**
 * Product mapper.
 */
public class ProductMapper {

    private ProductMapper() {
    }

    /**
     * Converts a product entity to a product DTO.
     *
     * @param product The product entity.
     * @return The product DTO.
     */
    public static ProductDTO toDto(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    /**
     * Converts a product DTO to a product entity.
     *
     * @param productDTO The product DTO.
     * @return The product entity.
     */
    public static Product toEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .build();
    }

    /**
     * Converts a list of product entities to a list of product DTOs.
     *
     * @param products The list of product entities.
     * @return The list of product DTOs.
     */
    public static List<ProductDTO> toDtoList(List<Product> products) {
        return products.stream().map(ProductMapper::toDto).toList();
    }
}
