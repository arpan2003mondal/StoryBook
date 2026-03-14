package com.company.storybook.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdatePriceRequest {

    @NotNull(message = "{storybook.price.required}")
    @DecimalMin(value = "0.0", inclusive = false, message = "{storybook.price.invalid}")
    private BigDecimal price;
}
