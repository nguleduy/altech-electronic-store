package com.example.altech.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Create product DTO.
 */
@Getter
@Setter
@Builder
public class CreateProductDTO {

    private String name;

    private BigDecimal price;
}
