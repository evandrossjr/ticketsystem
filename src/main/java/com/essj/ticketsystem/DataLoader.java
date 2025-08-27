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

        User admin = new User("admin","1234","admin@email.com", UserRole.ADMIN);
        userRepository.save(admin);

        User user = new User("user","1234","user@email.com", UserRole.USER);
        userRepository.save(user);


        User support = new User("support","1234","support@email.com", UserRole.SUPPORT_AGENT);
        userRepository.save(support);

    }
}
