package com.gamification.streaks.exception;

public class GamificationException extends RuntimeException {
    public GamificationException(String message) {
        super(message);
    }
    
    public GamificationException(String message, Throwable cause) {
        super(message, cause);
    }
}