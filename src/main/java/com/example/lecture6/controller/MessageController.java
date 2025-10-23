package com.example.lecture6.controller;

import com.example.lecture6.model.Message;
import com.example.lecture6.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Message> createMessage(@RequestBody CreateMessageRequest request) {
        return messageService.createMessage(request.message(), request.pinned());
    }

    @GetMapping
    public Flux<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/{id}")
    public Mono<Message> getMessageById(@PathVariable Long id) {
        return messageService.getMessageById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMessageById(@PathVariable Long id) {
        return messageService.deleteMessageById(id);
    }

    // Request DTO
    public record CreateMessageRequest(String message, Boolean pinned) {}
}