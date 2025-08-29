package com.instaapproval.api.dto;

import jakarta.validation.constraints.*;
import lombok.*;

public class AuthDtos {
    @Getter @Setter
    public static class RegisterRequest {
        @NotBlank private String username; // email or username
        @NotBlank @Size(min=6) private String password;
        @NotBlank private String firstName;
        @NotBlank private String lastName;
        @Email @NotBlank private String email;
        private String phone;
        private String address;
        private String role; // "CUSTOMER" or "ADMIN" (optional; default CUSTOMER)
    }

    @Getter @Setter
    public static class LoginRequest {
        @NotBlank private String username;
        @NotBlank private String password;
    }

    @Getter @Setter @AllArgsConstructor
    public static class AuthResponse {
        private String token;
        private String role;
    }
}
