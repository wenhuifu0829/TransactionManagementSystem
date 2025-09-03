package com.example.transactionmanager.service.impl;


import com.example.transactionmanager.entity.Transaction;
import com.example.transactionmanager.exception.InvalidTransactionException;
import com.example.transactionmanager.exception.TransactionAlreadyExistsException;
import com.example.transactionmanager.exception.TransactionNotFoundException;
import com.example.transactionmanager.repository.TransactionRepository;
import com.example.transactionmanager.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "transactions", allEntries = true),
            @CacheEvict(value = "transaction", key = "#result.id", condition = "#result != null")
    })
    public Transaction createTransaction(Transaction transaction) {
        // 验证交易金额
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException("Transaction amount must be greater than zero");
        }

        // 检查是否已存在相同transactionId的交易
        if (transactionRepository.existsByTransactionId(transaction.getTransactionId())) {
            throw new TransactionAlreadyExistsException("Transaction with ID " + transaction.getTransactionId() + " already exists");
        }

        return transactionRepository.save(transaction);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "transactions", allEntries = true),
            @CacheEvict(value = "transaction", key = "#id")
    })
    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new TransactionNotFoundException("Transaction with ID " + id + " not found");
        }
        transactionRepository.deleteById(id);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "transactions", allEntries = true),
            @CacheEvict(value = "transaction", key = "#id")
    })
    public Transaction updateTransaction(Long id, Transaction transaction) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction with ID " + id + " not found"));

        // 验证交易金额
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException("Transaction amount must be greater than zero");
        }

        // 更新字段
        existingTransaction.setAmount(transaction.getAmount());
        existingTransaction.setType(transaction.getType());
        existingTransaction.setDescription(transaction.getDescription());
        existingTransaction.setCategory(transaction.getCategory());

        return transactionRepository.save(existingTransaction);
    }

    @Override
    @Cacheable(value = "transactions", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<Transaction> getAllTransactions(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    @Override
    @Cacheable(value = "transaction", key = "#id")
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction with ID " + id + " not found"));
    }

    @Override
    @Cacheable(value = "transaction", key = "#transactionId")
    public Transaction getTransactionByTransactionId(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction with transaction ID " + transactionId + " not found"));
    }

    @Override
    @Cacheable(value = "transactions", key = "'type-' + #type + '-' + #pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<Transaction> getTransactionsByType(String type, Pageable pageable) {
        return transactionRepository.findByType(type, pageable);
    }

    @Override
    @Cacheable(value = "transactions", key = "'category-' + #category + '-' + #pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<Transaction> getTransactionsByCategory(String category, Pageable pageable) {
        return transactionRepository.findByCategory(category, pageable);
    }

    @Override
    @Cacheable(value = "transactions", key = "'amount-' + #minAmount + '-' + #maxAmount + '-' + #pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<Transaction> getTransactionsByAmountRange(BigDecimal minAmount, BigDecimal maxAmount, Pageable pageable) {
        return transactionRepository.findByAmountBetween(minAmount, maxAmount, pageable);
    }
}