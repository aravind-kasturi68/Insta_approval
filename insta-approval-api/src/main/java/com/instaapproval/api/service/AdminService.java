package com.instaapproval.api.service;

import com.instaapproval.api.entity.*;
import com.instaapproval.api.repo.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AdminService {
    private final LoanApplicationRepository loanRepo;
    private final ApprovalHistoryRepository histRepo;

    public AdminService(LoanApplicationRepository loanRepo, ApprovalHistoryRepository histRepo) {
        this.loanRepo = loanRepo; this.histRepo = histRepo;
    }

    public LoanApplication approve(User admin, Long loanId, String remarks) {
        LoanApplication app = loanRepo.findById(loanId).orElseThrow(() -> new RuntimeException("Not found"));
        if (app.getLoanAmount() != null && app.getLoanAmount() > 500000) {
            throw new RuntimeException("Amount exceeds 5L limit for auto approval/rejection by admin");
        }
        app.setStatus(LoanApplication.Status.APPROVED);
        app.setRemarks(remarks);
        LoanApplication saved = loanRepo.save(app);
        histRepo.save(ApprovalHistory.builder()
                .application(saved).admin(admin).action("Approved")
                .actionDate(LocalDateTime.now()).remarks(remarks).build());
        return saved;
    }

    public LoanApplication reject(User admin, Long loanId, String remarks) {
        LoanApplication app = loanRepo.findById(loanId).orElseThrow(() -> new RuntimeException("Not found"));
        if (app.getLoanAmount() != null && app.getLoanAmount() > 500000) {
            throw new RuntimeException("Amount exceeds 5L limit for admin decision");
        }
        app.setStatus(LoanApplication.Status.REJECTED);
        app.setRemarks(remarks);
        LoanApplication saved = loanRepo.save(app);
        histRepo.save(ApprovalHistory.builder()
                .application(saved).admin(admin).action("Rejected")
                .actionDate(LocalDateTime.now()).remarks(remarks).build());
        return saved;
    }
}
