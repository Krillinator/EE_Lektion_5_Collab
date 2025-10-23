package com.example.lecture6.service;

import com.example.lecture6.model.Message;
import com.example.lecture6.repository.MessageRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Mono<Message> createMessage(String messageText, Boolean pinned) {
        Message message = new Message(messageText, LocalDateTime.now(), pinned);
        return messageRepository.save(message);
    }

    public Flux<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Mono<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }

    public Mono<Void> deleteMessageById(Long id) {
        return messageRepository.deleteById(id);
    }
}