package com.instaapproval.api.service;

import com.instaapproval.api.entity.*;
import com.instaapproval.api.repo.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final CustomerRepository customerRepo;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepo, CustomerRepository customerRepo, BCryptPasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.customerRepo = customerRepo;
        this.encoder = encoder;
    }

    public User registerCustomer(String username, String rawPassword, String address) {
        if (userRepo.existsByUsername(username)) throw new RuntimeException("Username already exists");
        User user = User.builder()
                .username(username)
                .password(encoder.encode(rawPassword))
                .role(Role.CUSTOMER)
                .address(address)
                .build();
        return userRepo.save(user);
    }

    public User registerAdmin(String username, String rawPassword) {
        if (userRepo.existsByUsername(username)) throw new RuntimeException("Username already exists");
        User user = User.builder()
                .username(username)
                .password(encoder.encode(rawPassword))
                .role(Role.ADMIN)
                .build();
        return userRepo.save(user);
    }

    public Customer createCustomerProfile(User user, String firstName, String lastName, String email,
                                          String phone, String address) {
        Customer c = Customer.builder()
                .firstName(firstName).lastName(lastName).email(email)
                .phone(phone).address(address)
                .cibilScore(generateCibil()) // stub fetch
                .registrationDate(LocalDate.now())
                .user(user)
                .build();
        return customerRepo.save(c);
    }

    private int generateCibil() {
        // Placeholder for bank API integration. 700-850 typical "good" range; adjust as needed.
        return 720 + (int)(Math.random() * 80);
    }
}
