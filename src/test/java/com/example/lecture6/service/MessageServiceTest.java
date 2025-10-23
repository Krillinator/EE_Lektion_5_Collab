package com.example.lecture6.service;

import com.example.lecture6.model.Message;
import com.example.lecture6.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import reactor.test.StepVerifier;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    void clearDatabase() {
        messageRepository.deleteAll().block();
    }

    @Test
    void shouldCreateMessageAndRetrieveIt() {
        StepVerifier.create(
                        messageService.createMessage("Test message", false)
                                .flatMap(message -> messageRepository.findById(message.id()))
                )
                .expectNextMatches(message -> message.message().equals("Test message"))
                .verifyComplete();
    }

    @Test
    @Timeout(5)
    void shouldDeleteMessage() {
        Long id = messageService.createMessage("Delete me", false)
                .map(Message::id)
                .block();

        StepVerifier.create(messageService.deleteMessageById(id))
                .verifyComplete();

        StepVerifier.create(messageService.getAllMessages())
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void shouldGetAllMessages() {
        messageService.createMessage("Message 1", false).block();
        messageService.createMessage("Message 2", true).block();

        StepVerifier.create(messageService.getAllMessages())
                .expectNextCount(2)
                .verifyComplete();
    }
}