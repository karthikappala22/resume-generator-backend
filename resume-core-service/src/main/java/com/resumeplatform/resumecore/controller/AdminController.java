package com.resumeplatform.resumecore.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resumeplatform.resumecore.dto.CreateUserRequest;
import com.resumeplatform.resumecore.dto.UserResponse;
import com.resumeplatform.resumecore.service.UserService;

@RestController
@RequestMapping("/admin/users")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse createAdmin(@RequestBody CreateUserRequest request) {
        return userService.createAdmin(
                request.getEmail(),
                request.getFullName(),
                request.getPassword()
        );
    }
}

