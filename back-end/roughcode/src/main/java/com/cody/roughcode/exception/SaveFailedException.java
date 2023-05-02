package com.cody.roughcode.exception;

public class SaveFailedException extends RuntimeException {
    public SaveFailedException(String message) {
        super(message + " 등록에 실패했습니다");
    }

}
