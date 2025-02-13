package com.example.altech.service;

import com.example.altech.dto.RegisterUserDTO;
import com.example.altech.dto.UserDTO;
import com.example.altech.exception.InternalServerException;
import com.example.altech.exception.ResourceNotFoundException;
import com.example.altech.mapper.UserMapper;
import com.example.altech.model.User;
import com.example.altech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
     *
     * @return UserDetailsService
     */
    public UserDetailsService getUserDetailsService() {
        return username -> (UserDetails) userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("Username not found"));
    }

    /**
     * Find user by username.
     *
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
     *
     * @param request
     * @return username
     */
    public String saveUser(RegisterUserDTO request) {
        Optional<User> existingUser = userRepository.findByUsername(request.getUsername());
        if (existingUser.isPresent()) {
            throw new InternalServerException("Username already exists.");
        }
        UserDTO userDto = UserDTO.builder()
                .username(request.getUsername())
                .role(request.getRole())
                .name(request.getName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();
        User user = UserMapper.toEntity(userDto);
        String hashPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashPassword);
        userRepository.save(user);

        return user.getUsername();
    }

    /**
     * Retrieves all customers.
     *
     * @return List UserDTO
     */
    public List<UserDTO> getCustomers() {
        List<User> customers = userRepository.findByRole("CUSTOMER");
        return customers.stream().map(UserMapper::toDto).toList();
    }
}
