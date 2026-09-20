package com.bankflow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    public Account() {

    }

    public Account(String accountNumber, AccountType accountType, BigDecimal balance, AccountStatus status, Customer customer) {

        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.status = status;
        this.customer = customer;

    }

    public Long getId() {

        return id;

    }

    public void setId(Long id) {

        this.id = id;

    }

    public String getAccountNumber() {

        return accountNumber;

    }

    public void setAccountNumber(String accountNumber) {

        this.accountNumber = accountNumber;

    }

    public AccountType getAccountType() {

        return accountType;

    }

    public void setAccountType(AccountType accountType) {

        this.accountType = accountType;

    }

    public BigDecimal getBalance() {

        return balance;

    }

    public void setBalance(BigDecimal balance) {

        this.balance = balance;

    }

    public AccountStatus getStatus() {

        return status;

    }

    public void setStatus(AccountStatus status) {

        this.status = status;

    }

    public Customer getCustomer() {

        return customer;

    }

    public void setCustomer(Customer customer) {

        this.customer = customer;

    }




}