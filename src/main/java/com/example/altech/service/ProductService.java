package com.example.altech.service;

import com.example.altech.dto.CreateProductDTO;
import com.example.altech.dto.ProductDTO;
import com.example.altech.exception.BadRequestException;
import com.example.altech.exception.ResourceNotFoundException;
import com.example.altech.mapper.ProductMapper;
import com.example.altech.model.Product;
import com.example.altech.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Product Service.
 */
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    /**
     * Creates a new product.
     *
     * @param dto The product details.
     * @return The created product.
     */
    public ProductDTO createProduct(CreateProductDTO dto) {
        logger.info("Create product: {}", dto);
        if (dto.getName() == null || dto.getPrice() == null) {
            logger.error("Product name and price must not be null");
            throw new BadRequestException("Product name and price must not be null");
        }
        Product productEntity = ProductMapper.toEntity(
                ProductDTO.builder()
                        .name(dto.getName())
                        .price(dto.getPrice()).build());
        logger.info("Product created: {}", productEntity);
        return ProductMapper.toDto(productRepository.save(productEntity));
    }

    /**
     * Removes a product.
     *
     * @param productId The ID of the product to remove.
     */
    public void removeProduct(Long productId) {
        logger.info("Remove product: {}", productId);
        if (!productRepository.existsById(productId)) {
            logger.error("Product not found");
            throw new ResourceNotFoundException("Product not found");
        }
        productRepository.deleteById(productId);
        logger.info("Product removed: {}", productId);
    }

    /**
     * Gets all products.
     *
     * @return A list of all products.
     */
    public List<ProductDTO> getAllProducts() {
        logger.info("Get all products");
        return ProductMapper.toDtoList(productRepository.findAll());
    }

    /**
     * Gets a product by ID.
     *
     * @param id The ID of the product.
     * @return The product.
     */
    public ProductDTO getProduct(Long id) {
        logger.info("Get product: {}", id);
        return ProductMapper.toDto(
                productRepository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException("Product not found")));
    }
}

