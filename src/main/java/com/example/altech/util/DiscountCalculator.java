package com.example.altech.util;

import java.math.BigDecimal;

/**
 * Discount Calculator.
 */
public class DiscountCalculator {

    /**
     * Applies the buy one get half off promotion to the item total.
     *
     * @param itemTotal The item total.
     * @param price     The price of the item.
     * @param quantity  The quantity of the item.
     * @return The discounted item total.
     */
    public static BigDecimal applyBuyOneGetHalfOff(
            BigDecimal itemTotal, BigDecimal price, int quantity) {
        int discountedPairs = quantity / 2;
        BigDecimal discountAmount =
                price.multiply(BigDecimal.valueOf(0.5))
                        .multiply(BigDecimal.valueOf(discountedPairs));
        return itemTotal.subtract(discountAmount);
    }
}
