package com.example.lecture6.controller;

import com.example.lecture6.model.Message;
import com.example.lecture6.repository.MessageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class MessageControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    void clearDatabase() {
        messageRepository.deleteAll().block();
    }

    @Test
    void shouldCreateAndDeleteMessageSuccessfully() {
        Message created = webTestClient.post()
                .uri("/api/messages")
                .bodyValue(new MessageController.CreateMessageRequest("Test message", false))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Message.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.id());
        Assertions.assertEquals("Test message", created.message());

        Long id = created.id();

        webTestClient.delete()
                .uri("/api/messages/{id}", id)
                .exchange()
                .expectStatus().isNoContent();

        webTestClient.get()
                .uri("/api/messages/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }

    @Test
    void shouldGetAllMessages() {
        webTestClient.post()
                .uri("/api/messages")
                .bodyValue(new MessageController.CreateMessageRequest("Message 1", false))
                .exchange()
                .expectStatus().isCreated();

        webTestClient.post()
                .uri("/api/messages")
                .bodyValue(new MessageController.CreateMessageRequest("Message 2", true))
                .exchange()
                .expectStatus().isCreated();

        webTestClient.get()
                .uri("/api/messages")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Message.class)
                .hasSize(2);
    }
}