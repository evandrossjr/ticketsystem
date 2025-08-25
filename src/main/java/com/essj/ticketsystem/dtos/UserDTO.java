package com.essj.ticketsystem.dtos;

import com.essj.ticketsystem.models.enums.UserRole;

public record UserDTO(String username,
                      String email,
                      UserRole role) {
}
