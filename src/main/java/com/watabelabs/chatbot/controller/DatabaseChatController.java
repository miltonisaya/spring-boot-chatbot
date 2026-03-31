package com.watabelabs.chatbot.controller;

import com.watabelabs.chatbot.model.AgentMessageResponse;
import com.watabelabs.chatbot.model.UserMessageRequest;
import com.watabelabs.chatbot.service.DatabaseChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat")
public class DatabaseChatController {

    private final DatabaseChatService databaseChatService;

    public DatabaseChatController(DatabaseChatService databaseChatService) {
        this.databaseChatService = databaseChatService;
    }

    @PostMapping
    public ResponseEntity<AgentMessageResponse> chat(@RequestBody UserMessageRequest request) {
        String response = databaseChatService.chat(request.message());
        return ResponseEntity.ok(new AgentMessageResponse(response));
    }
}
