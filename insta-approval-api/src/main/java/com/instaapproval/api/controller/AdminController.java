package com.instaapproval.api.controller;

import com.instaapproval.api.dto.AdminDecisionRequest;
import com.instaapproval.api.entity.LoanApplication;
import com.instaapproval.api.entity.User;
import com.instaapproval.api.repo.UserRepository;
import com.instaapproval.api.service.AdminService;
import com.instaapproval.api.service.LoanService;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final LoanService loanService;
    private final AdminService adminService;
    private final UserRepository userRepo;

    public AdminController(LoanService loanService, AdminService adminService, UserRepository userRepo) {
        this.loanService = loanService; this.adminService = adminService; this.userRepo = userRepo;
    }

    private User me(Principal principal) {
        return userRepo.findByUsername(principal.getName()).orElseThrow();
    }

    @GetMapping("/loans/pending")
    public List<LoanApplication> pending() {
        return loanService.allPending();
    }

    @PutMapping("/loans/approve")
    public LoanApplication approve(Principal principal, @RequestBody AdminDecisionRequest req) {
        return adminService.approve(me(principal), req.getLoanId(), req.getRemarks());
    }

    @PutMapping("/loans/reject")
    public LoanApplication reject(Principal principal, @RequestBody AdminDecisionRequest req) {
        return adminService.reject(me(principal), req.getLoanId(), req.getRemarks());
    }
}
