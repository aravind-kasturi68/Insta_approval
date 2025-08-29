package com.instaapproval.api.repo;

import com.instaapproval.api.entity.ApprovalHistory;
import com.instaapproval.api.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApprovalHistoryRepository extends JpaRepository<ApprovalHistory, Long> {
    List<ApprovalHistory> findByApplication(LoanApplication app);
}
