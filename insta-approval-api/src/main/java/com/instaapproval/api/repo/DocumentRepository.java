package com.instaapproval.api.repo;

import com.instaapproval.api.entity.Document;
import com.instaapproval.api.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByApplication(LoanApplication app);
}
