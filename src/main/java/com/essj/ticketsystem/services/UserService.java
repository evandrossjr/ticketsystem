package com.essj.ticketsystem.services;


import com.essj.ticketsystem.dtos.UserDTO;
import com.essj.ticketsystem.exceptions.CustomAccessDeniedException;
import com.essj.ticketsystem.exceptions.ResourceNotFoundException;
import com.essj.ticketsystem.mappers.UserMapper;
import com.essj.ticketsystem.models.User;
import com.essj.ticketsystem.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id, UserDetails loggedInUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (!user.getUsername().equals(loggedInUser.getUsername())) {
            throw new CustomAccessDeniedException("Acesso negado: você não tem permissão para ver este perfil.");
        }
        return UserMapper.toDTO(user);
    }

    @Transactional(readOnly = true)
    public UserDTO findByUsernameIgnoreCase(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return UserMapper.toDTO(user);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAll(UserDetails loggedInUser){

        if (loggedInUser.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_SUPPORT_AGENT"))) {
            throw new CustomAccessDeniedException("Acesso negado: você não tem permissão para ver todos os usuários.");
        }

        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    public UserDTO save(UserDTO userDTO, UserDetails loggedInUser){

        if (!loggedInUser.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_SUPPORT_AGENT"))) {
            throw new CustomAccessDeniedException("Acesso negado: você não tem permissão para ver todos os usuários.");


        }


        User user = UserMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    public void deleteById(Long id){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public UserDTO update(Long id, UserDTO userDTO){
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        existingUser.setUsername(userDTO.username());
        existingUser.setEmail(userDTO.email());
        existingUser.setRole(userDTO.role());

        User updatedUser = userRepository.save(existingUser);
        return UserMapper.toDTO(updatedUser);
    }



}
