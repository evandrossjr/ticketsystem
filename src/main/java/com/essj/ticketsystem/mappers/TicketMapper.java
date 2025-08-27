package com.essj.ticketsystem.mappers;

import com.essj.ticketsystem.dtos.TicketDTO;
import com.essj.ticketsystem.models.Ticket;
import com.essj.ticketsystem.models.enums.TicketPriority;
import com.essj.ticketsystem.models.enums.TicketStatus;

public class TicketMapper {

    public static TicketDTO toDTO(Ticket ticket ){
        if (ticket == null) {
            return null;
        }
        return new TicketDTO(
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus().name(),
                ticket.getPriority().name(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt(),
                ticket.getUserSummary().getId()
        );
    }

    public static Ticket toEntity(TicketDTO ticketDTO) {
        if (ticketDTO == null) {
            return null;
        }
        Ticket ticket = new Ticket();
        ticket.setTitle(ticketDTO.title());
        ticket.setDescription(ticketDTO.description());
        ticket.setStatus(TicketStatus.valueOf(ticketDTO.status().toUpperCase()));
        ticket.setPriority(TicketPriority.valueOf(ticketDTO.priority().toUpperCase()));
        ticket.setCreatedAt(ticketDTO.createdAt());
        ticket.setUpdatedAt(ticketDTO.updatedAt());
        return ticket;
    }
}
