package com.company.storybook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {

    @NotBlank(message = "{user.password.required}")
    private String oldPassword;

    @NotBlank(message = "{user.password.required}")
    @Size(min = 6, message = "{user.password.size}")
    private String newPassword;
}
