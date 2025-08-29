package com.instaapproval.api.dto;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter
public class CustomerProfileUpdate {
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
}
