package com.bankflow.dto;

import com.bankflow.entity.AccountType;
import jakarta.validation.constraints.NotNull;

public class AccountRequest {

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    public AccountRequest() {

    }

    public AccountRequest(AccountType accountType) {

        this.accountType = accountType;

    }

    public AccountType getAccountType() {

        return accountType;

    }

    public void setAccountType(AccountType accountType) {

        this.accountType = accountType;

    }
}