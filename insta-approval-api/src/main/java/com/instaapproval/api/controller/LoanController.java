package com.instaapproval.api.controller;

import com.instaapproval.api.dto.*;
import com.instaapproval.api.entity.*;
import com.instaapproval.api.repo.LoanApplicationRepository;
import com.instaapproval.api.repo.UserRepository;
import com.instaapproval.api.service.CustomerService;
import com.instaapproval.api.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {
    private final LoanService loanService;
    private final CustomerService customerService;
    private final UserRepository userRepo;
    private final LoanApplicationRepository loanRepo;

    public LoanController(LoanService loanService, CustomerService customerService, UserRepository userRepo,
                          LoanApplicationRepository loanRepo) {
        this.loanService = loanService; this.customerService = customerService; this.userRepo = userRepo; this.loanRepo = loanRepo;
    }

    private Customer me(Principal principal) {
        User u = userRepo.findByUsername(principal.getName()).orElseThrow();
        return customerService.byUser(u).orElseThrow(() -> new RuntimeException("Customer profile not found"));
    }

    // Apply
    @PostMapping("/apply")
    public LoanApplication apply(Principal principal, @Valid @RequestBody LoanApplyRequest req) {
        return loanService.apply(me(principal), req.getLoanTypeId(), req.getLoanAmount(), req.getDescription());
    }

    // View my loans
    @GetMapping("/my")
    public List<LoanApplication> myLoans(Principal principal) {
        return loanService.forCustomer(me(principal));
    }

    // View status by ID
    @GetMapping("/status/{loanId}")
    public LoanApplication status(@PathVariable Long loanId) {
        return loanService.byId(loanId);
    }

    // Update pending
    @PutMapping("/update/{loanId}")
    public LoanApplication update(Principal principal, @PathVariable Long loanId, @Valid @RequestBody LoanUpdateRequest req) {
        LoanApplication app = loanService.byId(loanId);
        if (!app.getCustomer().getId().equals(me(principal).getId()))
            throw new RuntimeException("Not your application");
        return loanService.updateIfPending(app, req.getLoanAmount(), req.getDescription());
    }

    // Cancel pending
    @DeleteMapping("/cancel/{loanId}")
    public void cancel(Principal principal, @PathVariable Long loanId) {
        LoanApplication app = loanService.byId(loanId);
        if (!app.getCustomer().getId().equals(me(principal).getId()))
            throw new RuntimeException("Not your application");
        loanService.cancelIfPending(app);
    }

    // Document upload
    @PostMapping(value="/{loanId}/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Document upload(@PathVariable Long loanId,
                           @RequestPart("docType") String docType,
                           @RequestPart("file") MultipartFile file) throws Exception {
        return loanService.upload(loanId, docType, file);
    }

    @GetMapping("/{loanId}/documents")
    public List<Document> documents(@PathVariable Long loanId) {
        return loanService.docs(loanId);
    }
}
