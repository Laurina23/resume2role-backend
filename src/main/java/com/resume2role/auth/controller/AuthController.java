package com.resume2role.auth.controller;

import com.resume2role.auth.dto.LoginRequest;
import com.resume2role.auth.dto.RegisterRequest;
import com.resume2role.auth.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest req) {
        return authService.register(req);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody Map<String, String> body) {
        return authService.resetPassword(
                body.get("email"),
                body.get("password")
        );
    }
}