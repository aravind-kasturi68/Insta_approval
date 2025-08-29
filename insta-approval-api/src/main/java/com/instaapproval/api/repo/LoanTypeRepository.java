package com.instaapproval.api.repo;

import com.instaapproval.api.entity.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanTypeRepository extends JpaRepository<LoanType, Long> { }
