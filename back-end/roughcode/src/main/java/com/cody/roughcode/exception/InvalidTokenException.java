package com.cody.roughcode.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException() {
        super("Refresh token 정보가 유효하지 않음");
    }
}

