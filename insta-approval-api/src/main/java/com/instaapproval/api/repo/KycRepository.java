package com.instaapproval.api.repo;

import com.instaapproval.api.entity.Kyc;
import com.instaapproval.api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface KycRepository extends JpaRepository<Kyc, Long> {
    Optional<Kyc> findByCustomer(Customer customer);
}
