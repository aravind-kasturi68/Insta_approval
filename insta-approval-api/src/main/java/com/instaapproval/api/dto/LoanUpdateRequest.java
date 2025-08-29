package com.instaapproval.api.dto;

import lombok.*;
import jakarta.validation.constraints.*;


@Getter @Setter
public class LoanUpdateRequest {
    @NotNull @Min(10000) @Max(500000)
    private Integer loanAmount;
    private String description;
}
