package com.ailearning.ai_chatbot.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ailearning.ai_chatbot.DTO.ChatRequest;
import com.ailearning.ai_chatbot.DTO.ChatResponse;

@RestController
public class HelloController {
	private final ChatClient chatClient;
	
	public HelloController(@Value("${spring.ai.openai.api-key}")String apiKey) {
		
		//Groq API setup
		OpenAiApi groqApi = new OpenAiApi("https://api.groq.com/openai",apiKey);
		
		//model options
		OpenAiChatOptions options = OpenAiChatOptions.builder().model("llama-3.1-8b-instant").build();
		
		//model Instance
		OpenAiChatModel model = new OpenAiChatModel(groqApi, options);
		
		// ChatClient
		this.chatClient = ChatClient.create(model);
	}
	@PostMapping("/ask")
	
	public ChatResponse ask(@RequestBody ChatRequest request) {
		
		// 1. Validation (If this fails, GlobalExceptionHandler catches it)
	    if (request.getQuestion() == null || request.getQuestion().trim().isEmpty()) {
	        throw new IllegalArgumentException("Question cannot be empty");
	    }
	    String question = request.getQuestion();
	    String systemPrompt = request.getSystemPrompt();
		
		String answer = chatClient.prompt().system(systemPrompt).user(question).call().content();
		
		return new ChatResponse(question, answer,"llama-3.1-8b-instant",systemPrompt);
	}
	

}
