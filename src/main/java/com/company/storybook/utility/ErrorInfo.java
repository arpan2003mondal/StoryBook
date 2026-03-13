package com.company.storybook.utility;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorInfo {
    private int statusCode;
    private String message;
    private LocalDateTime timestamp;
}
