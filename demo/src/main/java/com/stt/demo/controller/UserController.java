package com.stt.demo.controller;

import com.stt.demo.dto.LoginResponse;
import com.stt.demo.dto.LoginRequest;
import com.stt.demo.dto.RegisterRequest;
import com.stt.demo.dto.UserResponse;
import com.stt.demo.model.User;
import com.stt.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserResponse registerUser(
            @Valid @RequestBody RegisterRequest request) {

        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public LoginResponse loginUser(
            @Valid @RequestBody LoginRequest request) {

        return userService.loginUser(
                request.getEmail(),
                request.getPassword()
        );
    }
}