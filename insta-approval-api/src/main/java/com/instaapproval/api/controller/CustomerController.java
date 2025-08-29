package com.instaapproval.api.controller;

import com.instaapproval.api.dto.CustomerProfileUpdate;
import com.instaapproval.api.entity.Customer;
import com.instaapproval.api.entity.User;
import com.instaapproval.api.repo.UserRepository;
import com.instaapproval.api.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final UserRepository userRepo;

    public CustomerController(CustomerService customerService, UserRepository userRepo) {
        this.customerService = customerService; this.userRepo = userRepo;
    }

    private Customer me(Principal principal) {
        User u = userRepo.findByUsername(principal.getName()).orElseThrow();
        return customerService.byUser(u).orElseThrow(() -> new RuntimeException("Customer profile not found"));
    }

    @GetMapping("/me")
    public Customer myProfile(Principal principal) {
        return me(principal);
    }

    @PutMapping("/me")
    public Customer updateProfile(Principal principal, @RequestBody CustomerProfileUpdate req) {
        Customer c = me(principal);
        if (req.getFirstName()!=null) c.setFirstName(req.getFirstName());
        if (req.getLastName()!=null)  c.setLastName(req.getLastName());
        if (req.getPhone()!=null)     c.setPhone(req.getPhone());
        if (req.getAddress()!=null)   c.setAddress(req.getAddress());
        if (req.getDateOfBirth()!=null) c.setDateOfBirth(req.getDateOfBirth());
        return customerService.save(c);
    }
}
