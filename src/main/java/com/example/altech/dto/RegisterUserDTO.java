package com.example.altech.dto;

import lombok.Data;

/**
 * Register user dto.
 */
@Data
public class RegisterUserDTO {

    private String username;
    private String password;
    private String role;
}
