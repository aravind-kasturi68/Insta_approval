package com.instaapproval.api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // customerId

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private String phone;
    private String address;
    private LocalDate dateOfBirth;

    private Integer cibilScore; // fetched by system
    private LocalDate registrationDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
