package com.company.storybook.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorRequest {

    @NotBlank(message = "{author.name.required}")
    private String name;

    private String bio;
}
