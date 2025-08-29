package com.instaapproval.api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ApprovalHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ApprovalID

    @ManyToOne
    private LoanApplication application;

    @ManyToOne
    private User admin; // AdminID

    private String action; // Approved / Rejected
    private LocalDateTime actionDate;
    private String remarks;
}
