package com.example.transactionmanager;



import com.example.transactionmanager.entity.Transaction;
import com.example.transactionmanager.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void createTransaction_ValidRequest_ReturnsCreated() throws Exception {
        String transactionJson = """
        {
            "amount": 150.75,
            "type": "DEPOSIT",
            "description": "Test deposit",
            "category": "INCOME"
        }
        """;

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount", is(150.75)))
                .andExpect(jsonPath("$.type", is("DEPOSIT")));
    }

    @Test
    void createTransaction_InvalidAmount_ReturnsBadRequest() throws Exception {
        String transactionJson = """
        {
            "amount": -50.00,
            "type": "DEPOSIT",
            "description": "Invalid amount"
        }
        """;

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllTransactions_ReturnsPaginatedResults() throws Exception {
        // First create a transaction
        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal("99.99"));
        transaction.setType("WITHDRAWAL");
        transaction.setDescription("Test withdrawal");
        transaction.setCategory("TEST");
        transactionRepository.save(transaction);

        mockMvc.perform(get("/api/transactions?page=0&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactions", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.currentPage", is(0)))
                .andExpect(jsonPath("$.totalItems", greaterThanOrEqualTo(1)));
    }

    @Test
    void deleteTransaction_ExistingId_ReturnsNoContent() throws Exception {
        // First create a transaction
        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal("50.00"));
        transaction.setType("DEPOSIT");
        transaction.setDescription("To be deleted");
        transaction.setCategory("TEST");
        Transaction saved = transactionRepository.save(transaction);

        mockMvc.perform(delete("/api/transactions/" + saved.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTransaction_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/transactions/9999"))
                .andExpect(status().isNotFound());
    }
}
