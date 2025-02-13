package com.example.altech.controller;

import com.example.altech.dto.BasketDTO;
import com.example.altech.dto.ReceiptDTO;
import com.example.altech.dto.UserDTO;
import com.example.altech.exception.ApiResponse;
import com.example.altech.service.BasketService;
import com.example.altech.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.altech.constant.PathDefinition.ADD_PRODUCT_TO_BASKET;
import static com.example.altech.constant.PathDefinition.CUSTOMER;
import static com.example.altech.constant.PathDefinition.GET_BASKET;
import static com.example.altech.constant.PathDefinition.GET_RECEIPT;
import static com.example.altech.constant.PathDefinition.REMOVE_PRODUCT_FROM_BASKET;

/**
 * Customer Controller.
 */
@RestController
@RequestMapping(CUSTOMER)
@RequiredArgsConstructor
@Tag(name = "Customer", description = "Customer management APIs")
public class CustomerController {

    private final BasketService basketService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Operation(summary = "Get customers",
            description = "Gets all customers")
    @GetMapping
    public ApiResponse<List<UserDTO>> getCustomers() {
        logger.info("GET /customers");
        return ApiResponse.success(userService.getCustomers());
    }

    /**
     * Adds an item to the basket.
     *
     * @param customerId
     * @param productId
     * @param quantity
     * @return ApiResponse
     */
    @Operation(summary = "Add to basket",
            description = "Adds an item to the basket")
    @PostMapping(ADD_PRODUCT_TO_BASKET)
    public ApiResponse<BasketDTO> addProductToBasket(
            @PathVariable Long customerId,
            @PathVariable Long productId,
            @RequestParam int quantity) {
        logger.info("POST /baskets/{}/{}", customerId, productId);
        return ApiResponse.success(basketService.addToBasket(customerId, productId, quantity));
    }

    /**
     * Removes an item from the basket.
     *
     * @param customerId
     * @param productId
     * @return ApiResponse
     */
    @Operation(summary = "Remove from basket",
            description = "Removes an item from the basket")
    @DeleteMapping(REMOVE_PRODUCT_FROM_BASKET)
    public ApiResponse<BasketDTO> removeProductFromBasket(
            @PathVariable Long customerId,
            @PathVariable Long productId,
            @RequestParam int quantity) {
        logger.info("DELETE /baskets/{}/{}", customerId, productId);
        return ApiResponse.success(
                basketService.removeProductFromBasket(customerId, productId, quantity));
    }

    /**
     * Gets the basket for a customer.
     *
     * @param customerId
     * @return ApiResponse List BasketDTO
     */
    @Operation(summary = "Get basket",
            description = "Gets the basket for a customer")
    @GetMapping(GET_BASKET)
    public ApiResponse<List<BasketDTO>> getBasket(@PathVariable Long customerId) {
        logger.info("GET /baskets/{}", customerId);
        return ApiResponse.success(basketService.getBasket(customerId));
    }

    /**
     * Gets the receipt for a customer.
     *
     * @param customerId
     * @return ApiResponse ReceiptDTO
     */
    @Operation(summary = "Get receipt",
            description = "Gets the receipt for a customer")
    @GetMapping(GET_RECEIPT)
    public ApiResponse<ReceiptDTO> getReceipt(@PathVariable Long customerId) {
        logger.info("GET /baskets/{}/receipt", customerId);
        return ApiResponse.success(basketService.calculateReceipt(customerId));
    }
}
