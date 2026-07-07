# AI Chatbot

A Spring Boot REST API that answers questions using Groq's LLaMA 3.1 model (via the OpenAI-compatible API), with a bundled React chat UI.

## Tech stack

| Component     | Technology                          |
|---------------|--------------------------------------|
| Language      | Java 21                              |
| Backend       | Spring Boot 3.5.16, Spring AI 1.0.0-M6 |
| AI provider   | Groq (`llama-3.1-8b-instant`)        |
| Frontend      | React + Vite                         |
| Build         | Maven (frontend build wired in via `frontend-maven-plugin`) |

## Project structure

```
src/main/java/com/ailearning/ai_chatbot/
  controller/HelloController.java     # POST /ask endpoint
  DTO/ChatRequest.java                # request body: question, systemPrompt
  DTO/ChatResponse.java               # response body: answer, model, timestamp, ...
  exception/GlobalExceptionHandler.java
  exception/ErrorResponse.java

frontend/                             # React + Vite chat UI
  src/App.jsx                         # chat window, calls POST /ask
  vite.config.js                      # dev proxy to :8080, builds into static/

src/main/resources/static/            # generated frontend build output (git-ignored)
```

## Prerequisites

- Java 21
- A free [Groq API key](https://console.groq.com)
- Node/npm are **not** required manually — Maven downloads an isolated Node 22 automatically during the build.

## Setup

`src/main/resources/application.properties` (git-ignored, so this step is per-machine) resolves the key from an external value:

```properties
spring.ai.openai.api-key=${openai.api-key}
```

Provide `openai.api-key` either as an environment variable or a JVM system property — see [Running with an environment variable](#running-with-an-environment-variable) below.

## Running the app

The simplest way — this builds the frontend automatically and starts the API:

```bash
./mvnw spring-boot:run
```

Open **http://localhost:8080** for the chat UI.

Or build a standalone jar:

```bash
./mvnw clean package
java -jar target/ai-chatbot-0.0.1-SNAPSHOT.jar
```

### Running with an environment variable

`application.properties` expects a property named `openai.api-key`. Shell environment variable names can't contain dots or dashes, so Spring's relaxed binding maps it to `OPENAI_API_KEY`:

```bash
export OPENAI_API_KEY=YOUR_GROQ_API_KEY
./mvnw spring-boot:run
```

If you'd rather keep the exact dotted name instead of the env var translation, pass it as a JVM system property to the forked app process:

```bash
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dopenai.api-key=YOUR_GROQ_API_KEY"
```

### Frontend development (hot reload)

Only needed if you're actively editing the UI:

```bash
# terminal 1
./mvnw spring-boot:run

# terminal 2
cd frontend
npm install
npm run dev
```

Open **http://localhost:5173** — Vite hot-reloads UI changes and proxies `/ask` requests to the backend on port 8080.

## API reference

### `POST /ask`

**Request**
```json
{
  "question": "What is Spring Boot?",
  "systemPrompt": "Optional custom system prompt"
}
```
`systemPrompt` defaults to a Java/Spring Boot backend assistant persona if omitted.

**Response — 200 OK**
```json
{
  "question": "What is Spring Boot?",
  "answer": "Spring Boot is...",
  "model": "llama-3.1-8b-instant",
  "systemPrompt": "You are a helpful AI assistant...",
  "answerLength": 187,
  "timestamp": 1719754800000
}
```

**Response — 400 Bad Request** (e.g. empty question)
```json
{
  "error": "BAD_REQUEST",
  "message": "Question cannot be empty",
  "timestamp": 1719754800000
}
```

**Response — 500 Internal Server Error** (e.g. upstream Groq failure)
```json
{
  "error": "ERROR",
  "message": "...",
  "timestamp": 1719754800000
}
```

### Example

```bash
curl -X POST http://localhost:8080/ask \
  -H "Content-Type: application/json" \
  -d '{"question": "What is Maven?"}'
```

## Troubleshooting

| Issue | Fix |
|---|---|
| `401`/invalid API key errors | Check that `OPENAI_API_KEY` is exported (or `openai.api-key` passed via JVM args) before running |
| Connection refused on :8080 | App isn't running — start it with `./mvnw spring-boot:run` |
| UI shows old content after frontend changes | Rebuild with `npm run build` (or use dev mode on :5173 for hot reload) |
| Slow first build | Maven is downloading Node 22 for the frontend build — one-time cost |
