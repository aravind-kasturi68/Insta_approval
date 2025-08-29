package com.instaapproval.api.repo;

import com.instaapproval.api.entity.Customer;
import com.instaapproval.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUser(User user);
    Optional<Customer> findByEmail(String email);
}
