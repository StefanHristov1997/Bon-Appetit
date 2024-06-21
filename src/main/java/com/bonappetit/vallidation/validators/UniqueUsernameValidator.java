package com.bonappetit.vallidation.validators;

import com.bonappetit.repository.UserRepository;
import com.bonappetit.vallidation.UniqueUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private String message;
    private final UserRepository userRepository;

    @Autowired
    public UniqueUsernameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {

        if (username == null) {
            return true;
        } else {
            boolean isUsernameExist = userRepository.findByUsername(username).isEmpty();

            if (isUsernameExist) {
                replaceDefaultConstraintViolation(context, message);
            }

            return isUsernameExist;
        }
    }

    private void replaceDefaultConstraintViolation(ConstraintValidatorContext context, String message) {
        context
                .unwrap(HibernateConstraintValidatorContext.class)
                .buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }
}
