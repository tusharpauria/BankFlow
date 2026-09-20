package com.bankflow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bank_transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TransactionType type;

    @Column(nullable = false)
    private String transactionReference;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public Transaction() {

    }

    public Transaction(BigDecimal amount, TransactionType type, String transactionReference, LocalDateTime timestamp, Account account) {

        this.amount = amount;
        this.type = type;
        this.transactionReference = transactionReference;
        this.timestamp = timestamp;
        this.account = account;

    }

    public Long getId() {

        return id;

    }

    public void setId(Long id) {

        this.id = id;

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

    public Account getAccount() {

        return account;

    }

    public void setAccount(Account account) {

        this.account = account;

    }
}