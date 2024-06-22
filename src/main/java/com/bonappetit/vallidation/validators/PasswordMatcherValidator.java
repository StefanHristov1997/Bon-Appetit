package com.bonappetit.vallidation.validators;

import com.bonappetit.model.dto.binding.UserRegisterDTO;
import com.bonappetit.vallidation.PasswordMatcher;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

public class PasswordMatcherValidator implements ConstraintValidator<PasswordMatcher, UserRegisterDTO> {

    private String message;

    @Override
    public void initialize(PasswordMatcher constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(UserRegisterDTO userRegisterDTO, ConstraintValidatorContext context) {

        if (userRegisterDTO == null) {
            return true;
        } else {
            boolean isPasswordMatch = userRegisterDTO.getPassword() != null && userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword());

            if (!isPasswordMatch) {
                HibernateConstraintValidatorContext hibernateContext =
                        context.unwrap(HibernateConstraintValidatorContext.class);

                hibernateContext
                        .buildConstraintViolationWithTemplate(message)
                        .addPropertyNode("confirmPassword")
                        .addConstraintViolation()
                        .disableDefaultConstraintViolation();
            }

            return isPasswordMatch;
        }
    }
}
