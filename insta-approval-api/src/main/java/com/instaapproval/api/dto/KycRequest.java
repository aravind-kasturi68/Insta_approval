package com.instaapproval.api.dto;

import lombok.*;

@Getter @Setter
public class KycRequest {
    private String idProof;
    private String addressProof;
}
