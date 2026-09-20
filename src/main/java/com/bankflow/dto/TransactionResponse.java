package com.bankflow.dto;

import com.bankflow.entity.Transaction;
import com.bankflow.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {

    private Long transactionId;
    private Long accountId;
    private BigDecimal amount;
    private TransactionType type;
    private String transactionReference;
    private LocalDateTime timestamp;

    public TransactionResponse() {

    }

    public TransactionResponse(Long transactionId, Long accountId, BigDecimal amount, TransactionType type, String transactionReference, LocalDateTime timestamp) {

        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.transactionReference = transactionReference;
        this.timestamp = timestamp;

    }

    public static TransactionResponse fromEntity(Transaction transaction) {

        return new TransactionResponse(
                transaction.getId(),
                transaction.getAccount().getId(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getTransactionReference(),
                transaction.getTimestamp()
        );

    }

    public Long getTransactionId() {

        return transactionId;

    }

    public void setTransactionId(Long transactionId) {

        this.transactionId = transactionId;

    }

    public Long getAccountId() {

        return accountId;

    }

    public void setAccountId(Long accountId) {

        this.accountId = accountId;

    }

    public BigDecimal getAmount() {

        return amount;

    }

    public void setAmount(BigDecimal amount) {

        this.amount = amount;

    }

    public TransactionType getType() {

        return type;

    }

    public void setType(TransactionType type) {

        this.type = type;

    }

    public String getTransactionReference() {

        return transactionReference;

    }

    public void setTransactionReference(String transactionReference) {

        this.transactionReference = transactionReference;

    }

    public LocalDateTime getTimestamp() {

        return timestamp;

    }

    public void setTimestamp(LocalDateTime timestamp) {

        this.timestamp = timestamp;

    }
}