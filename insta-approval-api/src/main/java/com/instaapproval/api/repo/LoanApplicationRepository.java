package com.instaapproval.api.repo;

import com.instaapproval.api.entity.LoanApplication;
import com.instaapproval.api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    List<LoanApplication> findByCustomer(Customer customer);
    List<LoanApplication> findByStatus(LoanApplication.Status status);
}
