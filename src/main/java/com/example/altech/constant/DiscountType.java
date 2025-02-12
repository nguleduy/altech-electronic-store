package com.example.altech.constant;

/**
 * Discount type.
 */
public enum DiscountType {
    BUY_ONE_GET_HALF_OFF("BUY_ONE_GET_HALF_OFF");

    private final String value;

    DiscountType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
