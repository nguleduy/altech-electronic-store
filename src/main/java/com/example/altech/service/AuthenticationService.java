package com.example.altech.service;

import com.example.altech.constant.TokenType;
import com.example.altech.dto.LoginRequestDTO;
import com.example.altech.dto.TokenResponseDTO;
import com.example.altech.exception.InvalidDataException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

/**
 * Authentication Service Impl.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    /**
     * Authenticate.
     *
     * @param loginRequestDto
     * @return TokenResponseDTO
     */
    public TokenResponseDTO authenticate(LoginRequestDTO loginRequestDto) {
        var user = userService.findByUsername(loginRequestDto.getUsername());

        // create new access token
        String accessToken = jwtService.generateToken(user);

        // create new refresh token
        String refreshToken = jwtService.generateRefreshToken(user);

        // save token to db
        return TokenResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    /**
     * Refresh token.
     *
     * @param request
     * @return TokenResponseDTO
     */
    public TokenResponseDTO refreshToken(HttpServletRequest request) {
        // validate
        final String refreshToken = request.getHeader(HttpHeaders.REFERER);
        if (refreshToken == null) {
            throw new InvalidDataException("Token must be not blank");
        }

        // extract user from token
        final String username = jwtService.extractUsername(refreshToken, TokenType.REFRESH_TOKEN);

        // check it into db
        var user = userService.findByUsername(username);
        if (!jwtService.isValid(refreshToken, TokenType.REFRESH_TOKEN, user)) {
            throw new InvalidDataException("Not allow access with this token");
        }

        // create new access token
        String accessToken = jwtService.generateToken(user);

        return TokenResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
