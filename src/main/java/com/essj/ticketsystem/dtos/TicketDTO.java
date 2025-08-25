package com.essj.ticketsystem.dtos;

import java.time.Instant;

public record TicketDTO(String title,
                        String description,
                        String status,
                        String priority,
                        Instant createdAt,
                        Instant updatedAt,
                        Long userId) {
}

