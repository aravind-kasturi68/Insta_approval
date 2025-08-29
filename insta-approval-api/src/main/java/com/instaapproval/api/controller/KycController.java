package com.instaapproval.api.controller;

import com.instaapproval.api.dto.KycRequest;
import com.instaapproval.api.entity.Customer;
import com.instaapproval.api.entity.Kyc;
import com.instaapproval.api.entity.User;
import com.instaapproval.api.repo.UserRepository;
import com.instaapproval.api.service.CustomerService;
import com.instaapproval.api.service.KycService;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/kyc")
public class KycController {
    private final KycService kycService;
    private final CustomerService customerService;
    private final UserRepository userRepo;

    public KycController(KycService kycService, CustomerService customerService, UserRepository userRepo) {
        this.kycService = kycService; this.customerService = customerService; this.userRepo = userRepo;
    }

    private Customer me(Principal principal) {
        User u = userRepo.findByUsername(principal.getName()).orElseThrow();
        return customerService.byUser(u).orElseThrow(() -> new RuntimeException("Customer profile not found"));
    }

    @PostMapping("/add")
    public Kyc add(Principal principal, @RequestBody KycRequest req) {
        return kycService.addOrUpdate(me(principal), req.getIdProof(), req.getAddressProof());
    }

    @PutMapping("/update")
    public Kyc update(Principal principal, @RequestBody KycRequest req) {
        return kycService.addOrUpdate(me(principal), req.getIdProof(), req.getAddressProof());
    }

    @GetMapping("/view")
    public Kyc view(Principal principal) {
        return kycService.view(me(principal));
    }
}
