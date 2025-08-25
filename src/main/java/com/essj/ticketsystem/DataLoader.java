package com.essj.ticketsystem;

import com.essj.ticketsystem.models.User;
import com.essj.ticketsystem.models.enums.UserRole;
import com.essj.ticketsystem.repositories.TicketRepository;
import com.essj.ticketsystem.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataLoader implements CommandLineRunner {

    private final TicketRepository ticketRepository;

    private final UserRepository userRepository;

    public DataLoader(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("1234");
        admin.setEmail("admin@email.com");
        admin.setRole(UserRole.valueOf("ADMIN"));
        userRepository.save(admin);

        User user = new User();
        user.setUsername("user");
        user.setPassword("1234");
        user.setEmail("user@email.com");
        user.setRole(UserRole.valueOf("USER"));
        userRepository.save(user);


        User support = new User();
        support.setUsername("support");
        support.setPassword("1234");
        support.setEmail("support@email.com");
        support.setRole(UserRole.valueOf("SUPPORT_AGENT"));
        userRepository.save(support);

    }
}
