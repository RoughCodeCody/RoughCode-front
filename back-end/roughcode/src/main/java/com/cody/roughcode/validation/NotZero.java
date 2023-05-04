package com.cody.roughcode.validation;

import com.cody.roughcode.validation.NotZeroValidator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotZeroValidator.class)
public @interface NotZero {
    String message() default "0은 입력할 수 없습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

