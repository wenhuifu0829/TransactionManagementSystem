package com.example.transactionmanager;


import com.example.transactionmanager.entity.Transaction;
import com.example.transactionmanager.exception.InvalidTransactionException;
import com.example.transactionmanager.exception.TransactionAlreadyExistsException;
import com.example.transactionmanager.exception.TransactionNotFoundException;
import com.example.transactionmanager.repository.TransactionRepository;
import com.example.transactionmanager.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setTransactionId("test-transaction-123");
        transaction.setAmount(new BigDecimal("100.00"));
        transaction.setType("DEPOSIT");
        transaction.setDescription("Test transaction");
        transaction.setCategory("TEST");
    }

    @Test
    void createTransaction_ValidTransaction_ReturnsSavedTransaction() {
        when(transactionRepository.existsByTransactionId(any())).thenReturn(false);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionService.createTransaction(transaction);

        assertNotNull(result);
        assertEquals(transaction.getId(), result.getId());
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void createTransaction_DuplicateTransactionId_ThrowsException() {
        when(transactionRepository.existsByTransactionId(any())).thenReturn(true);

        assertThrows(TransactionAlreadyExistsException.class, () -> {
            transactionService.createTransaction(transaction);
        });
    }

    @Test
    void createTransaction_InvalidAmount_ThrowsException() {
        transaction.setAmount(new BigDecimal("-10.00"));

        assertThrows(InvalidTransactionException.class, () -> {
            transactionService.createTransaction(transaction);
        });
    }

    @Test
    void getTransactionById_ExistingId_ReturnsTransaction() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        Transaction result = transactionService.getTransactionById(1L);

        assertNotNull(result);
        assertEquals(transaction.getId(), result.getId());
    }

    @Test
    void getTransactionById_NonExistingId_ThrowsException() {
        when(transactionRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () -> {
            transactionService.getTransactionById(999L);
        });
    }

    @Test
    void deleteTransaction_ExistingId_DeletesTransaction() {
        when(transactionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(transactionRepository).deleteById(1L);

        assertDoesNotThrow(() -> {
            transactionService.deleteTransaction(1L);
        });

        verify(transactionRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTransaction_NonExistingId_ThrowsException() {
        when(transactionRepository.existsById(999L)).thenReturn(false);

        assertThrows(TransactionNotFoundException.class, () -> {
            transactionService.deleteTransaction(999L);
        });
    }
}