package com.watabelabs.chatbot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Database Chatbot API",
        version = "1.0",
        description = "Query any PostgreSQL database using plain, everyday language. The AI agent translates your question into SQL, executes it, and returns a human-readable answer."
    )
)
public class ChatbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatbotApplication.class, args);
	}

}
