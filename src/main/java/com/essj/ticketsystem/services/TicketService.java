package com.essj.ticketsystem.services;


import com.essj.ticketsystem.dtos.TicketDTO;
import com.essj.ticketsystem.exceptions.ResourceNotFoundException;
import com.essj.ticketsystem.mappers.TicketMapper;
import com.essj.ticketsystem.models.Ticket;
import com.essj.ticketsystem.models.enums.TicketPriority;
import com.essj.ticketsystem.models.enums.TicketStatus;
import com.essj.ticketsystem.repositories.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketService {


    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional(readOnly = true)
    public List<TicketDTO> findAll(){
        return ticketRepository.findAll().stream().map(TicketMapper::toDTO).toList();
    }


    @Transactional(readOnly = true)
    public TicketDTO findById(Long id){
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));

        return TicketMapper.toDTO(ticket);
    }

    @Transactional
    public TicketDTO save(TicketDTO ticketDTO){
        Ticket ticket = TicketMapper.toEntity(ticketDTO);
        Ticket savedTicket = ticketRepository.save(ticket);
        return TicketMapper.toDTO(savedTicket);
    }


    @Transactional
    public void deleteById(Long id){
        if(!ticketRepository.existsById(id)){
            throw new ResourceNotFoundException("Ticket not found with id: " + id);
        }
        ticketRepository.deleteById(id);
    }

    @Transactional
    public TicketDTO update(Long id, TicketDTO ticketDTO){
        Ticket existingTicket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));

        existingTicket.setTitle(ticketDTO.title());
        existingTicket.setDescription(ticketDTO.description());

        // Atualiza os enums apenas se o DTO tiver valores para eles
        if (ticketDTO.status() != null) {
            existingTicket.setStatus(TicketStatus.valueOf(ticketDTO.status()));
        }
        if (ticketDTO.priority() != null) {
            existingTicket.setPriority(TicketPriority.valueOf(ticketDTO.priority()));
        }

        Ticket updatedTicket = ticketRepository.save(existingTicket);
        return TicketMapper.toDTO(updatedTicket);
    }

}
