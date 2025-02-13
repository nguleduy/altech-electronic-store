package com.example.altech.service;

import com.example.altech.dto.LoginRequestDTO;
import com.example.altech.dto.RegisterUserDTO;
import com.example.altech.dto.UserDTO;
import com.example.altech.mapper.UserMapper;
import com.example.altech.model.User;
import com.example.altech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * User service.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Get user details.
     * @return UserDetailsService
     */
    public UserDetailsService getUserDetailsService() {
        return username -> (UserDetails) userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("Username not found"));
    }

    /**
     * Find user by username.
     * @param username
     * @return UserDTO
     */
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));

        return UserMapper.toDto(user);

    }

    /**
     * Save user.
     * @param request
     * @return username
     */
    public String saveUser(RegisterUserDTO request) {
        UserDTO userDto = UserDTO.builder()
                .username(request.getUsername())
                .role(request.getRole())
                .build();
        User user = UserMapper.toEntity(userDto);
        String hashPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashPassword);
        userRepository.save(user);

        return user.getUsername();
    }
}
