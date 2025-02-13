package com.example.altech.controller;

import com.example.altech.dto.LoginRequestDTO;
import com.example.altech.dto.RegisterUserDTO;
import com.example.altech.dto.TokenResponseDTO;
import com.example.altech.exception.ApiResponse;
import com.example.altech.service.AuthenticationService;
import com.example.altech.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.altech.constant.PathDefinition.AUTHENTICATION;
import static com.example.altech.constant.PathDefinition.LOGIN;
import static com.example.altech.constant.PathDefinition.REFRESH_TOKEN;
import static com.example.altech.constant.PathDefinition.REGISTER_USER;

/**
 * Authentication Controller.
 */
@RestController
@RequestMapping(AUTHENTICATION)
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    /**
     * Login.
     *
     * @param loginRequestDto
     * @return TokenResponseDTO
     */
    @Operation(summary = "Login",
            description = "User login")
    @PostMapping(LOGIN)
    public ApiResponse<TokenResponseDTO> login(
            @RequestBody LoginRequestDTO loginRequestDto) {
        try {
            TokenResponseDTO tokenResponseDto =
                    authenticationService.authenticate(loginRequestDto);
            logger.info("Login successfully.");
            return ApiResponse.success(tokenResponseDto);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Refresh token.
     *
     * @param request
     * @return TokenResponseDTO
     */
    @Operation(summary = "Refresh token",
            description = "Refresh token")
    @PostMapping(REFRESH_TOKEN)
    public ApiResponse<TokenResponseDTO> refresh(HttpServletRequest request) {
        try {
            TokenResponseDTO tokenResponseDto = authenticationService.refreshToken(request);
            logger.info("Refresh token successfully.");
            return ApiResponse.success(tokenResponseDto);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Register user.
     *
     * @param registerUserDTO
     * @return String
     */
    @Operation(summary = "Register user",
            description = "Register user")
    @PostMapping(REGISTER_USER)
    public ApiResponse<String> register(@RequestBody RegisterUserDTO registerUserDTO) {
        try {
            String username = userService.saveUser(registerUserDTO);
            logger.info("Register user successfully.");
            return ApiResponse.success(username);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }
}

