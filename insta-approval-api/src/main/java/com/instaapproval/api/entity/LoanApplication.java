package com.instaapproval.api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LoanApplication {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ApplicationID/loanId

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private LoanType loanType;

    private Integer loanAmount; // up to 500000
    private String description; // loandescription

    @Enumerated(EnumType.STRING)
    private Status status; // PENDING/APPROVED/REJECTED

    private String remarks; // reason/remarks (admin)

    private LocalDateTime applicationDate;

    public enum Status { PENDING, APPROVED, REJECTED }
}
