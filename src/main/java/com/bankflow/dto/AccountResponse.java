package com.bankflow.dto;

import com.bankflow.entity.Account;
import com.bankflow.entity.AccountStatus;
import com.bankflow.entity.AccountType;

import java.math.BigDecimal;

public class AccountResponse {

    private Long accountId;
    private String accountNumber;
    private AccountType accountType;
    private BigDecimal balance;
    private AccountStatus status;
    private Long customerId;

    public AccountResponse() {

    }

    public AccountResponse(Long accountId, String accountNumber, AccountType accountType, BigDecimal balance, AccountStatus status, Long customerId) {

        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.status = status;
        this.customerId = customerId;

    }

    public static AccountResponse fromEntity(Account account) {

        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus(),
                account.getCustomer().getId()
        );

    }

    public Long getAccountId() {

        return accountId;

    }

    public void setAccountId(Long accountId) {

        this.accountId = accountId;

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

    public Long getCustomerId() {

        return customerId;

    }

    public void setCustomerId(Long customerId) {

        this.customerId = customerId;

    }
}