package com.ailearning.ai_chatbot.DTO;

//Request DTO Class
public class ChatRequest {
	private String question;
	private String systemPrompt;
	
	//Empty Constructor
	public ChatRequest() {
		this.systemPrompt = getDefaultSystemPrompt();
	}
	
	// Constructor with question only
	public ChatRequest(String question) {
		this.question = question;
		this.systemPrompt = getDefaultSystemPrompt();
	}
	
	// Constructor with both
    public ChatRequest(String question, String systemPrompt) {
        this.question = question;
        this.systemPrompt = systemPrompt;
    }
    
 // Default System Prompt 
    private static String getDefaultSystemPrompt() {
        return "You are a helpful AI assistant specializing in Java, " +
               "Spring Boot, and backend development. " +
               "Provide clear, concise, and accurate answers.";
    }

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getSystemPrompt() {
		return systemPrompt;
	}

	public void setSystemPrompt(String systemPrompt) {
		this.systemPrompt = systemPrompt;
	}
	
	
	
	
}
