package com.essj.ticketsystem.controller.api;


import com.essj.ticketsystem.dtos.TicketDTO;
import com.essj.ticketsystem.services.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@Tag(name = "Tickets", description = "Ticket management API")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/")
    @Operation(summary = "Get all tickets", description = "Return a list of all tickets.")
    @ApiResponse(responseCode = "200", description = "Tickets retrieved successfully",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketDTO.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        List<TicketDTO> tickets = ticketService.findAll();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get ticket by ID", description = "Return a single ticket identified by their ID.")
    @ApiResponse(responseCode = "200", description = "Ticket found successfully",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketDTO.class)))
    @ApiResponse(responseCode = "404", description = "Ticket not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Long id) {
        TicketDTO ticketDTO = ticketService.findById(id);
        return ResponseEntity.ok(ticketDTO);
    }

    @PostMapping("/")
    @Operation(summary = "Create a new ticket", description = "Create a new ticket with the provided details.")
    @ApiResponse(responseCode = "201", description = "Ticket created successfully",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketDTO.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO) {
        TicketDTO createdTicket = ticketService.save(ticketDTO);
        return ResponseEntity.status(201).body(createdTicket);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing ticket", description = "Update the details of an existing ticket identified by their ID.")
    @ApiResponse(responseCode = "200", description = "Ticket updated successfully",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketDTO.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "Ticket not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<TicketDTO> updateTicket(@PathVariable Long id, @RequestBody TicketDTO ticketDTO) {
        TicketDTO updatedTicket = ticketService.update(id, ticketDTO);
        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a ticket", description = "Delete an existing ticket identified by their ID.")
    @ApiResponse(responseCode = "204", description = "Ticket deleted successfully")
    @ApiResponse(responseCode = "404", description = "Ticket not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
