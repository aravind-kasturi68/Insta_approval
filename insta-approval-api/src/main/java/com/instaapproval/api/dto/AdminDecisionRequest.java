package com.instaapproval.api.dto;

import lombok.*;
import jakarta.validation.constraints.*;
@Getter @Setter
public class AdminDecisionRequest {
    @NotNull private Long loanId;
    private String remarks;
}