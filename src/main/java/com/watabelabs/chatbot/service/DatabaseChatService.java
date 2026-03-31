package com.watabelabs.chatbot.service;

import com.watabelabs.chatbot.service.agent.DatabaseChatAgent;
import com.watabelabs.chatbot.service.tool.SqlQueryTool;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class DatabaseChatService {

    @Value("${ollama.base-url:http://localhost:11434}")
    private String ollamaBaseUrl;

    @Value("${ollama.model:llama3.2}")
    private String ollamaModel;

    private final SqlQueryTool sqlQueryTool;
    private DatabaseChatAgent agent;

    public DatabaseChatService(SqlQueryTool sqlQueryTool) {
        this.sqlQueryTool = sqlQueryTool;
    }

    @PostConstruct
    void init() {
        OllamaChatModel chatModel = OllamaChatModel.builder()
                .baseUrl(ollamaBaseUrl)
                .modelName(ollamaModel)
                .temperature(0.2)
                .timeout(Duration.ofSeconds(60))
                .build();

        agent = AiServices.builder(DatabaseChatAgent.class)
                .chatModel(chatModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(50))
                .tools(sqlQueryTool)
                .build();
    }

    public String chat(String message) {
        return agent.chat(message);
    }
}
