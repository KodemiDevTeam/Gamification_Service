package com.gamification.streaks.execption;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}