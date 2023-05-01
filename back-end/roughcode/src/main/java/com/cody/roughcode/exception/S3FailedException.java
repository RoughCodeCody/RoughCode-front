package com.cody.roughcode.exception;

import org.springframework.dao.DataAccessException;

public class S3FailedException extends DataAccessException {
    public S3FailedException(String message) {
        super(message);
    }
}
