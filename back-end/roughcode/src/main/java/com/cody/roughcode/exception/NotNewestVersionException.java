package com.cody.roughcode.exception;

import org.springframework.dao.DataAccessException;

public class NotNewestVersionException extends DataAccessException {
    public NotNewestVersionException(String message) {
        super(message);
    }
    public NotNewestVersionException() {
        super("최신 버전이 아닙니다");
    }
}
