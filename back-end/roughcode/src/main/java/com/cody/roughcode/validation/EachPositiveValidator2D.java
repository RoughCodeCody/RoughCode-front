package com.cody.roughcode.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class EachPositiveValidator2D implements ConstraintValidator<EachPositive2D, List<List<Integer>>> {

    @Override
    public void initialize(EachPositive2D constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<List<Integer>> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null 값은 검증에서 제외
        }
        for (List<Integer> innerList : value) {
            for (Integer number : innerList) {
                if (number == null || number <= 0) {
                    return false;
                }
            }
        }
        return true;
    }
}