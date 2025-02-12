package com.example.altech.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Receipt item DTO.
 */
@Getter
@AllArgsConstructor
public class ReceiptItemDTO {

    private String productName;
    private Integer quantity;
    private BigDecimal amount;
}
