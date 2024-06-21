package com.bonappetit.vallidation;

import com.bonappetit.vallidation.validators.PasswordMatcherValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = PasswordMatcherValidator.class)
public @interface PasswordMatcher {
    String message() default "{user.password-match}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
