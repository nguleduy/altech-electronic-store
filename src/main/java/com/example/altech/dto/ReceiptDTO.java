package com.example.altech.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Receipt DTO.
 */
@Getter
@Setter
@AllArgsConstructor
public class ReceiptDTO {

    private Long customerId;
    private List<ReceiptItemDTO> receiptItems;
    private BigDecimal totalPrice;
}
