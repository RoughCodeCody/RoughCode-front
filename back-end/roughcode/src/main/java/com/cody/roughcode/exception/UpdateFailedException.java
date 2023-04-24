package com.cody.roughcode.exception;

import org.springframework.dao.DataAccessException;

public class UpdateFailedException extends DataAccessException {
    public UpdateFailedException(String message) {
        super(message);
    }
}
