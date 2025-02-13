package com.example.altech.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Token response dto.
 */
@Data
@Builder
public class TokenResponseDTO {

    private String accessToken;
    private String refreshToken;
    private Long userId;
    private String username;
    private String role;
}
