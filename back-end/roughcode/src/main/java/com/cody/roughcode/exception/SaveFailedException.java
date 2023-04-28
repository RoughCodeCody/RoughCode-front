package com.cody.roughcode.exception;

import org.springframework.dao.DataAccessException;

public class SaveFailedException extends DataAccessException {
    public SaveFailedException(String message) {
        super(message);
    }
}
