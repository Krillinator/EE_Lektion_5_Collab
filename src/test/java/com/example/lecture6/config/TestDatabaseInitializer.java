package com.example.lecture6.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
@Profile("test")
public class TestDatabaseInitializer {
    
    @Bean
    CommandLineRunner initTestDatabase(ConnectionFactory connectionFactory) {
        return args -> {
            DatabaseClient client = DatabaseClient.create(connectionFactory);
            
            client.sql("DROP TABLE IF EXISTS messages").fetch().rowsUpdated()
                .then(client.sql(
                    "CREATE TABLE messages (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "message VARCHAR(255) NOT NULL, " +
                    "created_at TIMESTAMP NOT NULL, " +
                    "pinned BOOLEAN NOT NULL DEFAULT FALSE" +
                    ")"
                ).fetch().rowsUpdated())
                .subscribe(
                    result -> System.out.println("✅ Test database initialized!"),
                    error -> System.err.println("❌ Error: " + error.getMessage())
                );
        };
    }
}
