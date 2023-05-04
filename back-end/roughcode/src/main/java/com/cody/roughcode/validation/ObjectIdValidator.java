package com.cody.roughcode.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ObjectIdValidator implements ConstraintValidator<ObjectId, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        try {
            new org.bson.types.ObjectId(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}