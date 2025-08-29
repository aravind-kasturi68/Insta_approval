package com.instaapproval.api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Document {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // DocumentID

    @ManyToOne
    private LoanApplication application;

    private String documentType; // IDProof, AddressProof, IncomeProof
    private String filePath;
    private LocalDateTime uploadDate;
}
