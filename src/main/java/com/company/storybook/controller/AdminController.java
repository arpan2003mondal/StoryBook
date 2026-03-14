package com.company.storybook.controller;

import com.company.storybook.dto.LoginRequest;
import com.company.storybook.dto.StorybookRequest;
import com.company.storybook.dto.StorybookResponse;
import com.company.storybook.dto.AuthorRequest;
import com.company.storybook.dto.CategoryRequest;
import com.company.storybook.dto.UpdatePriceRequest;
import com.company.storybook.exception.StoryBookException;
import com.company.storybook.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<String> adminLogin(@Valid @RequestBody LoginRequest loginRequest) throws StoryBookException {
        String token = adminService.adminLogin(loginRequest);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/storybooks")
    public ResponseEntity<StorybookResponse> addStorybook(@Valid @RequestBody StorybookRequest storybookRequest) throws StoryBookException {
        StorybookResponse response = adminService.addStorybook(storybookRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/storybooks/{id}/price")
    public ResponseEntity<StorybookResponse> updateStorybookPrice(@PathVariable Long id, @Valid @RequestBody UpdatePriceRequest request) throws StoryBookException {
        StorybookResponse response = adminService.updateStorybookPrice(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authors")
    public ResponseEntity<Object> addAuthor(@Valid @RequestBody AuthorRequest authorRequest) throws StoryBookException {
        Object response = adminService.addAuthor(authorRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorRequest authorRequest) throws StoryBookException {
        Object response = adminService.updateAuthor(id, authorRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) throws StoryBookException {
        String message = adminService.deleteAuthor(id);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/categories")
    public ResponseEntity<Object> addCategory(@Valid @RequestBody CategoryRequest categoryRequest) throws StoryBookException {
        Object response = adminService.addCategory(categoryRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest categoryRequest) throws StoryBookException {
        Object response = adminService.updateCategory(id, categoryRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) throws StoryBookException {
        String message = adminService.deleteCategory(id);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> adminLogout(@RequestHeader("Authorization") String authHeader) throws StoryBookException {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new StoryBookException("user.logout.invalid.token");
        }
        String token = authHeader.substring(7);
        String message = adminService.logout(token);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
