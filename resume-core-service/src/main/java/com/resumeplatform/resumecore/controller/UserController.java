package com.resumeplatform.resumecore.controller;

import com.resumeplatform.resumecore.dto.CreateUserRequest;
import com.resumeplatform.resumecore.dto.UserResponse;
import com.resumeplatform.resumecore.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request) {

        return ResponseEntity.ok(userService.createUser(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {

        return ResponseEntity.ok(userService.getUser(id));
    }
}
