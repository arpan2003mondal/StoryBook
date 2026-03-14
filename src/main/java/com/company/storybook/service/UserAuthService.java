package com.company.storybook.service;

import com.company.storybook.dto.RegisterRequest;
import com.company.storybook.dto.LoginRequest;
import com.company.storybook.exception.StoryBookException;

public interface UserAuthService {
    String registerUser(RegisterRequest registerRequest) throws StoryBookException;
    String loginUser(LoginRequest loginRequest) throws StoryBookException;
    String logout(String token);
}
