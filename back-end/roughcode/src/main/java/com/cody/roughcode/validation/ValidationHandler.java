package com.cody.roughcode.validation;

import com.cody.roughcode.util.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String message = error.getDefaultMessage();
            errors.add(message);
        });
        return (ResponseEntity<Object>) Response.badRequest(errors.get(0));
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<?> handleConstraintViolation(ConstraintViolationException e, WebRequest request) {
        return Response.badRequest(e.getMessage());
    }
}