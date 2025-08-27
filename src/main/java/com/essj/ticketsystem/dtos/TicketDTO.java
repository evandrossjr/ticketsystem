package com.essj.ticketsystem.dtos;

import java.time.Instant;
import java.time.LocalDateTime;

public record TicketDTO(String title,
                        String description,
                        String status,
                        String priority) {
}

