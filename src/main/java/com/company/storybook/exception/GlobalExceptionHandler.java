package com.company.storybook.exception;

import com.company.storybook.utility.ErrorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(StoryBookException.class)
    public ResponseEntity<ErrorInfo> handleStoryBookException(StoryBookException ex) {
        String message = messageSource.getMessage(ex.getMessage(), null, ex.getMessage(), Locale.ENGLISH);
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorInfo.setMessage(message);
        errorInfo.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorInfo.setMessage(message);
        errorInfo.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> handleGenericException(Exception ex) {
        String message = messageSource.getMessage("general.error", null, "An unexpected error occurred.", Locale.ENGLISH);
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorInfo.setMessage(message);
        errorInfo.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}