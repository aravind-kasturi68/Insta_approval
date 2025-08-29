package com.instaapproval.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable=false)
    private String username; // email or username

    @Column(nullable=false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Role role;

    // Optional basic profile fields
    private String address;
}
