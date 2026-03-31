# Database Chatbot

A Spring Boot application that lets you query any PostgreSQL database using plain, everyday language. You send a question in natural language via a REST API, and the app translates it into SQL, executes it, and returns a human-readable answer — no SQL knowledge required.

## How it works

1. You send a `POST /api/chat` request with a natural-language question.
2. The app calls a local [Ollama](https://ollama.com) LLM.
3. The LLM inspects the database schema, writes a SQL `SELECT` query, executes it, and summarises the results in plain language.
4. The answer is returned as a JSON response.

The LLM is only allowed to run read-only `SELECT` queries. Any attempt to modify data is refused.

## Prerequisites

- Java 25+
- PostgreSQL running and accessible
- [Ollama](https://ollama.com) running locally with a model pulled, e.g.:
  ```
  ollama pull llama3.2
  ```

## Configuration

Edit `src/main/resources/application.properties`:

```properties
# PostgreSQL datasource
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password

# Ollama
ollama.base-url=http://localhost:11434
ollama.model=llama3.2
```

## Running the app

```bash
./gradlew bootRun
```

The server starts on port `8080`.

## API

### `POST /api/chat`

Send a natural-language question about your database.

**Request**
```json
{
  "message": "How many users signed up last month?"
}
```

**Response**
```json
{
  "response": "There were 143 users who signed up last month (February 2026)."
}
```

## Project structure

```
src/main/java/com/watabelabs/chatbot/
├── controller/
│   └── DatabaseChatController.java   # REST endpoint
├── service/
│   ├── DatabaseChatService.java      # Builds and owns the AI agent
│   ├── agent/
│   │   └── DatabaseChatAgent.java    # LangChain4j agent interface + system prompt
│   └── tool/
│       └── SqlQueryTool.java         # Tools the LLM calls: getSchema(), executeQuery()
└── model/
    ├── UserMessageRequest.java       # Incoming JSON body
    └── AgentMessageResponse.java     # Outgoing JSON body
```

## Technology stack

| Component | Technology |
|---|---|
| Framework | Spring Boot 4 |
| AI / LLM orchestration | LangChain4j 1.12.2 |
| LLM runtime | Ollama (local) |
| Database | PostgreSQL |
| Build tool | Gradle |
