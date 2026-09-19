package com.bankflow.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransactionRequest {

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    public TransactionRequest() {

    }

    public TransactionRequest(BigDecimal amount) {

        this.amount = amount;

    }

    public BigDecimal getAmount() {

        return amount;

    }

    public void setAmount(BigDecimal amount) {

        this.amount = amount;

    }
}