package com.company.storybook.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {

    @NotBlank(message = "{category.name.required}")
    private String name;

    private String description;
}
