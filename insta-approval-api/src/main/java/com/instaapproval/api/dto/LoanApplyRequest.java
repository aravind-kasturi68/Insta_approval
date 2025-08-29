package com.instaapproval.api.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Getter @Setter
public class LoanApplyRequest {
    @NotNull private Long loanTypeId;
    @NotNull @Min(10000) @Max(500000)
    private Integer loanAmount;
    private String description;
}