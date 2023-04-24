package com.cody.roughcode.exception;

public class NotMatchException extends RuntimeException {
    public NotMatchException(String message) {
        super(message);
    }

    public NotMatchException() {
        super("접근 권한이 없습니다");
    }
}

