package com.instaapproval.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Kyc {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Customer customer;

    private String idProof;      // simple text fields; (alternate: store file paths)
    private String addressProof; // simple text fields
}
