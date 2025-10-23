package com.example.lecture6.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("messages")
public record Message(
        @Id
        Long id,

        @Column("message")
        String message,

        @Column("created_at")
        LocalDateTime createdAt,

        @Column("pinned")
        Boolean pinned
) {
    // Constructor för att skapa nya meddelanden (utan ID)
    public Message(String message, LocalDateTime createdAt, Boolean pinned) {
        this(null, message, createdAt, pinned);
    }
}