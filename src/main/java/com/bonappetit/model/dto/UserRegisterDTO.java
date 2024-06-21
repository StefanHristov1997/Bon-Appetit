package com.bonappetit.model.dto;

import com.bonappetit.vallidation.PasswordMatcher;
import com.bonappetit.vallidation.UniqueEmail;
import com.bonappetit.vallidation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordMatcher
public class UserRegisterDTO {

    @NotBlank(message = "{not.blank}")
    @Size(min = 3, max = 20, message = "{user.username.length}")
    @UniqueUsername
    String username;

    @NotBlank(message = "{empty.user.email}")
    @Email(message = "{user.email}")
    @UniqueEmail
    String email;

    @NotBlank(message = "{not.blank}")
    @Size(min = 3, max = 20, message = "{user.password.length}")
    String password;

    @NotBlank(message = "{not.blank}")
    @Size(min = 3, max = 20, message = "{user.confirm-password.length}")
    String confirmPassword;
}
