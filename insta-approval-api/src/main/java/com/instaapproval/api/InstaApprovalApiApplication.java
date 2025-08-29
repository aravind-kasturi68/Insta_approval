package com.instaapproval.api;

import com.instaapproval.api.entity.LoanType;
import com.instaapproval.api.repo.LoanTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InstaApprovalApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstaApprovalApiApplication.class, args);
    }

    // ✅ This runs automatically after the app starts
    @Bean
    CommandLineRunner seedLoanTypes(LoanTypeRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(LoanType.builder()
                        .typeName("Interest-Free Car Loan")
                        .description("Zero interest on the loan")
                        .build());
                repo.save(LoanType.builder()
                        .typeName("Car Loan 0% First Year")
                        .description("0% interest for the first year, normal interest later")
                        .build());

                System.out.println("✅ Loan types seeded successfully!");
            }
        };
    }
}
