package com.hiennhatt.vod.validations;

import com.hiennhatt.vod.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserValidation {
    @NotBlank
    private String username;

    @Email
    private String email;

    @NotBlank
    private String password;

    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
