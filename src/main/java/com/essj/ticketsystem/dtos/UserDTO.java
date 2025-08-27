package com.essj.ticketsystem.dtos;

import com.essj.ticketsystem.models.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import javax.management.relation.Role;

public record UserDTO(@NotBlank String username,
                      @NotBlank @Email String email,
                      @NotNull UserRole role) {
}
