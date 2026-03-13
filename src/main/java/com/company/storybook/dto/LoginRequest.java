package com.company.storybook.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "{user.email.required}")
    private String email;

    @NotBlank(message = "{user.password.required}")
    private String password;

}
