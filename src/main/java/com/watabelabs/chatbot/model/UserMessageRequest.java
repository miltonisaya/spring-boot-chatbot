package com.watabelabs.chatbot.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "A natural-language question to ask about the database")
public record UserMessageRequest(
    @Schema(description = "The question, written in plain language", example = "How many users signed up last month?")
    String message
) {}
