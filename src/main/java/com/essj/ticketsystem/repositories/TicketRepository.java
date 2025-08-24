package com.essj.ticketsystem.repositories;

import com.essj.ticketsystem.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository <Ticket, Long> {
}
