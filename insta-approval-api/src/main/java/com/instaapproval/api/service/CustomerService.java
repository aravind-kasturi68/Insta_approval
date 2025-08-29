package com.instaapproval.api.service;

import com.instaapproval.api.entity.Customer;
import com.instaapproval.api.entity.User;
import com.instaapproval.api.repo.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository repo;
    public CustomerService(CustomerRepository repo) { this.repo = repo; }

    public Optional<Customer> byUser(User user) { return repo.findByUser(user); }
    public Customer save(Customer c) { return repo.save(c); }
}
