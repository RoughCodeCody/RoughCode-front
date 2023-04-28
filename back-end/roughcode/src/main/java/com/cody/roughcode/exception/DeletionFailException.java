package com.cody.roughcode.exception;

public class DeletionFailException extends RuntimeException {
    public DeletionFailException(String message) {
        super(message + " 삭제에 실패했습니다");
    }
}

