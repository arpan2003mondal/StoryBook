package com.company.storybook.service;

import com.company.storybook.dto.LoginRequest;
import com.company.storybook.dto.StorybookRequest;
import com.company.storybook.dto.StorybookResponse;
import com.company.storybook.exception.StoryBookException;

public interface AdminService {

    String adminLogin(LoginRequest loginRequest) throws StoryBookException;

    StorybookResponse addStorybook(StorybookRequest storybookRequest) throws StoryBookException;

    String logout(String token);
}
