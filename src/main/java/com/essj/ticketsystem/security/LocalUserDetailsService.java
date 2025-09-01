package com.essj.ticketsystem.security;

import com.essj.ticketsystem.models.User;
import com.essj.ticketsystem.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class LocalUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public LocalUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Log para remover depois dos testes
        System.out.println("Tentando carregar o usuário: " + username);

        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> {
                    System.out.println("Usuário não encontrado apagar deposi");
                    return new UsernameNotFoundException("User not found with username: " + username);
                });

        System.out.println("Usuário encontrado: " + user.getUsername());
        System.out.println("Roles: " + user.getRole());
        System.out.println("Classe: " + user.getClass().getSimpleName());

        return new LocalUserDetails(user);

    }
}
