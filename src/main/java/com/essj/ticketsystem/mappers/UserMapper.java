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
        if (userDTO == null) return null;
        return new User(userDTO.username(), userDTO.email(), userDTO.role());
    }

}
