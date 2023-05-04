package com.cody.roughcode.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ObjectIdValidator.class)
public @interface ObjectId {

    String message() default "Invalid ObjectId";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}