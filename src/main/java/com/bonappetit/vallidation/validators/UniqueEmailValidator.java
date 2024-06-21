package com.bonappetit.vallidation.validators;

import com.bonappetit.repository.UserRepository;
import com.bonappetit.vallidation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private String message;

    private final UserRepository userRepository;

    @Autowired
    public UniqueEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        if (email == null) {
            return true;
        } else {
            boolean isEmailExist = userRepository.findByEmail(email).isEmpty();

            if (isEmailExist) {
                replaceDefaultConstraintViolation(context, message);
            }

            return isEmailExist;
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
