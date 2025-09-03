package com.example.transactionmanager.data;

import com.example.transactionmanager.entity.Transaction;
import com.example.transactionmanager.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author fuwh34757
 * @version 1.0
 * @project transaction-management
 * @description TODO
 * @date 2025/9/3 19:31:13
 */

@Component
public class DataLoader implements CommandLineRunner {

    private final TransactionRepository transactionRepository;

    public DataLoader(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 添加一些初始测试数据
        if (transactionRepository.count() == 0) {
            transactionRepository.save(new Transaction(new BigDecimal("100.50"), "DEPOSIT", "Salary", "INCOME"));
            transactionRepository.save(new Transaction(new BigDecimal("25.75"), "WITHDRAWAL", "Groceries", "FOOD"));
            transactionRepository.save(new Transaction(new BigDecimal("15.00"), "WITHDRAWAL", "Lunch", "FOOD"));
            transactionRepository.save(new Transaction(new BigDecimal("45.30"), "WITHDRAWAL", "Gas", "TRANSPORT"));
            transactionRepository.save(new Transaction(new BigDecimal("200.00"), "DEPOSIT", "Freelance work", "INCOME"));

            System.out.println("Sample data loaded successfully!");
        }
    }
}