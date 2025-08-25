package com.essj.ticketsystem.mappers;

import com.essj.ticketsystem.dtos.UserDTO;
import com.essj.ticketsystem.models.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        user.setRole(userDTO.role());
        return user;
    }
}
