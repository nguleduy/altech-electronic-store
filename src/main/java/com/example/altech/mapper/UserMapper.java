package com.example.altech.mapper;

import com.example.altech.dto.UserDTO;
import com.example.altech.model.User;

/**
 * User mapper.
 */
public class UserMapper {

    private UserMapper() {
    }

    /**
     * Convert entity to dto.
     *
     * @param user
     * @return
     */
    public static UserDTO toDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .name(user.getName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .build();
    }

    /**
     * Convert dto to entity.
     *
     * @param dto
     * @return
     */
    public static User toEntity(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .role(dto.getRole())
                .name(dto.getName())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .build();
    }
}
