package com.watabelabs.chatbot.controller;

import com.watabelabs.chatbot.model.AgentMessageResponse;
import com.watabelabs.chatbot.model.UserMessageRequest;
import com.watabelabs.chatbot.service.DatabaseChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@Tag(name = "Database Chat", description = "Ask questions about your database in plain language")
public class DatabaseChatController {

    private final DatabaseChatService databaseChatService;

    public DatabaseChatController(DatabaseChatService databaseChatService) {
        this.databaseChatService = databaseChatService;
    }

    @Operation(
        summary = "Send a natural-language question",
        description = "The AI agent inspects the database schema, generates a SQL query, executes it, and returns a human-readable answer."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Answer from the AI agent",
        content = @Content(schema = @Schema(implementation = AgentMessageResponse.class))
    )
    @PostMapping
    public ResponseEntity<AgentMessageResponse> chat(@RequestBody UserMessageRequest request) {
        String response = databaseChatService.chat(request.message());
        return ResponseEntity.ok(new AgentMessageResponse(response));
    }
}
