package com.watabelabs.chatbot.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "The AI agent's answer to the user's question")
public record AgentMessageResponse(
    @Schema(description = "A human-readable summary of the query results", example = "There were 143 users who signed up last month.")
    String response
) {}
