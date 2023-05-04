package com.cody.roughcode.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class EachPositiveValidator implements ConstraintValidator<EachPositive, List<Long>> {

    @Override
    public void initialize(EachPositive constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<Long> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null 값은 검증에서 제외
        }
        for (Long number : value) {
            if (number <= 0) {
                return false;
            }
        }
        return true;
    }
}