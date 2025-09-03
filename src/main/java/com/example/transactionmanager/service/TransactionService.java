package com.example.transactionmanager.service;

import com.example.transactionmanager.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);

    void deleteTransaction(Long id);

    Transaction updateTransaction(Long id, Transaction transaction);

    Page<Transaction> getAllTransactions(Pageable pageable);

    Transaction getTransactionById(Long id);

    Transaction getTransactionByTransactionId(String transactionId);

    Page<Transaction> getTransactionsByType(String type, Pageable pageable);

    Page<Transaction> getTransactionsByCategory(String category, Pageable pageable);

    Page<Transaction> getTransactionsByAmountRange(BigDecimal minAmount, BigDecimal maxAmount, Pageable pageable);
}
