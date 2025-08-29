package com.instaapproval.api.service;

import com.instaapproval.api.entity.*;
import com.instaapproval.api.repo.*;
import com.instaapproval.api.util.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanService {
    private final LoanApplicationRepository loanRepo;
    private final LoanTypeRepository typeRepo;
    private final DocumentRepository docRepo;
    private final FileStorageService storage;

    public LoanService(LoanApplicationRepository loanRepo, LoanTypeRepository typeRepo,
                       DocumentRepository docRepo, FileStorageService storage) {
        this.loanRepo = loanRepo; this.typeRepo = typeRepo; this.docRepo = docRepo; this.storage = storage;
    }

    public LoanApplication apply(Customer customer, Long loanTypeId, Integer amount, String description) {
        LoanType type = typeRepo.findById(loanTypeId).orElseThrow(() -> new RuntimeException("Loan type not found"));
        LoanApplication app = LoanApplication.builder()
                .customer(customer).loanType(type)
                .loanAmount(amount).description(description)
                .status(LoanApplication.Status.PENDING)
                .applicationDate(LocalDateTime.now())
                .build();
        return loanRepo.save(app);
    }

    public LoanApplication updateIfPending(LoanApplication app, Integer amount, String description) {
        if (app.getStatus() != LoanApplication.Status.PENDING) {
            throw new RuntimeException("Only pending applications can be updated");
        }
        app.setLoanAmount(amount);
        app.setDescription(description);
        return loanRepo.save(app);
    }

    public void cancelIfPending(LoanApplication app) {
        if (app.getStatus() != LoanApplication.Status.PENDING) {
            throw new RuntimeException("Only pending applications can be cancelled");
        }
        loanRepo.delete(app);
    }

    public List<LoanApplication> forCustomer(Customer c) { return loanRepo.findByCustomer(c); }
    public List<LoanApplication> allPending() { return loanRepo.findByStatus(LoanApplication.Status.PENDING); }
    public LoanApplication byId(Long id) { return loanRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found")); }

    public Document upload(Long appId, String docType, MultipartFile file) throws IOException {
        LoanApplication app = byId(appId);
        String path = storage.save(appId, docType, file);
        Document doc = Document.builder().application(app).documentType(docType)
                .filePath(path).uploadDate(LocalDateTime.now()).build();
        return docRepo.save(doc);
    }

    public List<Document> docs(Long appId) {
        LoanApplication app = byId(appId);
        return docRepo.findByApplication(app);
    }
}
