package com.instaapproval.api.controller;

import com.instaapproval.api.config.JwtService;
import com.instaapproval.api.dto.AuthDtos.*;
import com.instaapproval.api.entity.Role;
import com.instaapproval.api.entity.User;
import com.instaapproval.api.service.UserService;
import com.instaapproval.api.repo.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;
    private final JwtService jwt;

    public AuthController(UserService userService, UserRepository userRepo,
                          BCryptPasswordEncoder encoder, JwtService jwt) {
        this.userService = userService; this.userRepo = userRepo; this.encoder = encoder; this.jwt = jwt;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest req) {
        String roleStr = (req.getRole() == null || req.getRole().isBlank()) ? "CUSTOMER" : req.getRole().toUpperCase();
        Role role = Role.valueOf(roleStr);
        User user;
        if (role == Role.ADMIN) {
            user = userService.registerAdmin(req.getUsername(), req.getPassword());
        } else {
            user = userService.registerCustomer(req.getUsername(), req.getPassword(), req.getAddress());
            userService.createCustomerProfile(user, req.getFirstName(), req.getLastName(), req.getEmail(), req.getPhone(), req.getAddress());
        }
        String token = jwt.generate(user.getUsername(), user.getRole().name());
        return new AuthResponse(token, user.getRole().name());
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req) {
        User user = userRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwt.generate(user.getUsername(), user.getRole().name());
        return new AuthResponse(token, user.getRole().name());
    }
}
