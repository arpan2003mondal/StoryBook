package com.company.storybook.controller;

import com.company.storybook.dto.RegisterRequest;
import com.company.storybook.dto.LoginRequest;
import com.company.storybook.exception.StoryBookException;
import com.company.storybook.service.UserAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserAuthController {

    @Autowired
    private UserAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest registerRequest) throws StoryBookException {
        String message = authService.registerUser(registerRequest);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequest loginRequest) throws StoryBookException {
        String token = authService.loginUser(loginRequest);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) throws StoryBookException {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new StoryBookException("user.logout.invalid.token");
        }
        String token = authHeader.substring(7);
        String message = authService.logout(token);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
