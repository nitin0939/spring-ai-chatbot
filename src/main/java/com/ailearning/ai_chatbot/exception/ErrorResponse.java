package com.ailearning.ai_chatbot.exception;

public class ErrorResponse {
	
	private String error;
    private String message;
    private long timestamp;

    // Constructor
    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters
    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    // Setters (optional)
    public void setError(String error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
