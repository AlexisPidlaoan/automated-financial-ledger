package com.project.automated_financial_ledger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
    "com.project.automated_financial_ledger",
    "com.project.automated_financial_ledger_system"
})
@EntityScan(basePackages = "com.project.automated_financial_ledger_system.model")
@EnableJpaRepositories(basePackages = "com.project.automated_financial_ledger_system.repository")
public class AutomatedFinancialLedgerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutomatedFinancialLedgerApplication.class, args);
    }
}