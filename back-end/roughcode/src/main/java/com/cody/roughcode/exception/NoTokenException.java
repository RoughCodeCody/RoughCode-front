package com.cody.roughcode.exception;

public class NoTokenException extends RuntimeException {
    public NoTokenException(String message) {
        super(message);
    }

    public NoTokenException() {
        super("토큰이 없습니다");
    }
}

