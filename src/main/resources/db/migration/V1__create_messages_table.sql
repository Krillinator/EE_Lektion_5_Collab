CREATE TABLE messages (
                          id BIGSERIAL PRIMARY KEY,
                          message VARCHAR(255) NOT NULL,
                          created_at TIMESTAMP NOT NULL,
                          pinned BOOLEAN NOT NULL DEFAULT FALSE
);