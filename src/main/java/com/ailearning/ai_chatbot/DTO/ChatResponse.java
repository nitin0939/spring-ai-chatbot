package com.ailearning.ai_chatbot.DTO;

//Response DTO Class
public class ChatResponse {

	private String question;
	private String answer;
	private String model;
	private String systemPrompt;      // ← NEW FIELD!
    private int answerLength; 		  // ← NEW FIELD!
	private long timestamp;
	
	public ChatResponse(String question, String answer, String model) {
        this.question = question;
        this.answer = answer;
        this.model = model;
        this.systemPrompt = "Default Java Expert";
        this.timestamp = System.currentTimeMillis();
        this.answerLength = answer.length();
    }
	
	 public ChatResponse(String question, String answer, 
             String model, String systemPrompt) {
		 this.question = question;
		 this.answer = answer;
		 this.model = model;
		 this.systemPrompt = systemPrompt;
		 this.timestamp = System.currentTimeMillis();
		 this.answerLength = answer.length();
}

	 public String getQuestion() {
	        return question;
	    }

	    public String getAnswer() {
	        return answer;
	    }

	    public String getModel() {
	        return model;
	    }

	    public String getSystemPrompt() {
	        return systemPrompt;
	    }

	    public long getTimestamp() {
	        return timestamp;
	    }

	    public int getAnswerLength() {
	        return answerLength;
	    }

	    // All Setters (optional)
	    public void setQuestion(String question) {
	        this.question = question;
	    }

	    public void setAnswer(String answer) {
	        this.answer = answer;
	    }

	    public void setModel(String model) {
	        this.model = model;
	    }

	    public void setSystemPrompt(String systemPrompt) {
	        this.systemPrompt = systemPrompt;
	    }
	
	
}
