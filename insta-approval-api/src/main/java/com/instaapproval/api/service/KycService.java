package com.instaapproval.api.service;

import com.instaapproval.api.entity.Customer;
import com.instaapproval.api.entity.Kyc;
import com.instaapproval.api.repo.KycRepository;
import org.springframework.stereotype.Service;

@Service
public class KycService {
    private final KycRepository repo;
    public KycService(KycRepository repo) { this.repo = repo; }

    public Kyc addOrUpdate(Customer customer, String idProof, String addressProof) {
        Kyc kyc = repo.findByCustomer(customer).orElse(Kyc.builder().customer(customer).build());
        kyc.setIdProof(idProof);
        kyc.setAddressProof(addressProof);
        return repo.save(kyc);
    }

    public Kyc view(Customer customer) {
        return repo.findByCustomer(customer).orElse(null);
    }
}
