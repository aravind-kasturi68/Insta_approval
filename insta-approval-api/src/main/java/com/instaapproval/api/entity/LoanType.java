package com.instaapproval.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LoanType {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // LoanTypeID
    private String typeName;      // "Interest-Free Car Loan" / "Car Loan 0% First Year"
    private String description;
}
