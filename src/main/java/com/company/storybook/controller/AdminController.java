package com.company.storybook.controller;

import com.company.storybook.dto.LoginRequest;
import com.company.storybook.dto.StorybookRequest;
import com.company.storybook.dto.StorybookResponse;
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
}
