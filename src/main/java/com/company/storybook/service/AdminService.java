package com.company.storybook.service;

import com.company.storybook.dto.*;
import com.company.storybook.exception.StoryBookException;

public interface AdminService {

    String adminLogin(LoginRequest loginRequest) throws StoryBookException;

    StorybookResponse addStorybook(StorybookRequest storybookRequest) throws StoryBookException;

    StorybookResponse updateStorybookPrice(Long storybookId, UpdatePriceRequest request) throws StoryBookException;

    String logout(String token);

    // Author Management
    Object addAuthor(AuthorRequest authorRequest) throws StoryBookException;

    Object updateAuthor(Long authorId, AuthorRequest authorRequest) throws StoryBookException;

    String deleteAuthor(Long authorId) throws StoryBookException;

    // Category Management
    Object addCategory(CategoryRequest categoryRequest) throws StoryBookException;

    Object updateCategory(Long categoryId, CategoryRequest categoryRequest) throws StoryBookException;

    String deleteCategory(Long categoryId) throws StoryBookException;
}
