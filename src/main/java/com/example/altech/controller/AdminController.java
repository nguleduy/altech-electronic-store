package com.example.altech.controller;

import com.example.altech.dto.CreateProductDTO;
import com.example.altech.dto.DiscountDTO;
import com.example.altech.dto.ProductDTO;
import com.example.altech.exception.ApiResponse;
import com.example.altech.service.DiscountService;
import com.example.altech.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.altech.constant.PathDefinition.ADD_DISCOUNT;
import static com.example.altech.constant.PathDefinition.ADMIN;
import static com.example.altech.constant.PathDefinition.CREATE_PRODUCT;
import static com.example.altech.constant.PathDefinition.GET_DISCOUNT;
import static com.example.altech.constant.PathDefinition.GET_PRODUCTS;
import static com.example.altech.constant.PathDefinition.GET_PRODUCT_DETAIL;
import static com.example.altech.constant.PathDefinition.REMOVE_PRODUCT;

/**
 * Admin Controller.
 */
@RestController
@RequestMapping(ADMIN)
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin management APIs")
public class AdminController {
    private final ProductService productService;
    private final DiscountService discountService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    /**
     * Create a new product.
     *
     * @param product
     * @return ApiResponse ProductDTO
     */
    @Operation(summary = "Create product",
            description = "Creates a new product in the store")
    @PostMapping(CREATE_PRODUCT)
    public ApiResponse<ProductDTO> createProduct(@RequestBody CreateProductDTO product) {
        logger.info("Create product: {}", product);
        return ApiResponse.success(productService.createProduct(product));
    }

    /**
     * Get all products.
     *
     * @return ApiResponse List ProductDTO
     */
    @Operation(summary = "Get all products",
            description = "Returns a list of all products in the store")
    @GetMapping(GET_PRODUCTS)
    public ApiResponse<List<ProductDTO>> getProducts() {
        logger.info("Get all products");
        return ApiResponse.success(productService.getAllProducts());
    }

    /**
     * Get a product by ID.
     *
     * @param id
     * @return ApiResponse ProductDTO
     */
    @Operation(summary = "Get product",
            description = "Returns the details of a product in the store")
    @GetMapping(GET_PRODUCT_DETAIL)
    public ApiResponse<ProductDTO> getProduct(@PathVariable Long id) {
        logger.info("Get product: {}", id);
        return ApiResponse.success(productService.getProduct(id));
    }

    /**
     * Remove a product by ID.
     *
     * @param id
     * @return ApiResponse Long
     */
    @Operation(summary = "Remove product",
            description = "Removes a product from the store")
    @DeleteMapping(REMOVE_PRODUCT)
    public ApiResponse<Long> removeProduct(@PathVariable Long id) {
        logger.info("Remove product: {}", id);
        productService.removeProduct(id);
        return ApiResponse.success(id);
    }

    /**
     * Add a discount to a product.
     *
     * @param productId
     * @param promotionType
     * @return ResponseEntity DiscountDTO
     */
    @Operation(summary = "Add discount", description = "Adds a discount to a product")
    @PostMapping(ADD_DISCOUNT)
    public ResponseEntity<DiscountDTO> addDiscount(
            @PathVariable Long productId,
            @RequestParam String promotionType) {
        logger.info("Add discount for product: {}", productId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(discountService.addDiscount(productId, promotionType));
    }

    /**
     * Get discounts for a product.
     *
     * @param productId
     * @return ResponseEntity List DiscountDTO
     */
    @Operation(summary = "Get discounts", description = "Returns a list of discounts for a product")
    @GetMapping(GET_DISCOUNT)
    public ResponseEntity<List<DiscountDTO>> getDiscount(
            @PathVariable Long productId) {
        logger.info("Get discounts for product: {}", productId);
        return ResponseEntity.ok(discountService.getDiscount(productId));
    }
}

