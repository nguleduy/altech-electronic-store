package com.example.altech.constant;

/**
 * Path definition.
 */
public class PathDefinition {

    public static final String API_V1 = "/api/v1";
    public static final String ADMIN = API_V1 + "/admin";
    public static final String CUSTOMER = API_V1 + "/customer";
    public static final String CREATE_PRODUCT = "/products";
    public static final String GET_PRODUCTS = "/products";
    public static final String GET_PRODUCT_DETAIL = "/products/{id}";
    public static final String REMOVE_PRODUCT = "/products/{id}";
    public static final String ADD_DISCOUNT = "/discounts/{productId}";
    public static final String GET_DISCOUNT = "/discounts/{productId}";
    public static final String ADD_PRODUCT_TO_BASKET = "/baskets/{customerId}/{productId}";
    public static final String REMOVE_PRODUCT_FROM_BASKET = "/baskets/{basketId}";
    public static final String GET_BASKET = "/baskets/{customerId}";
    public static final String GET_RECEIPT = "/baskets/{customerId}/receipt";
}
