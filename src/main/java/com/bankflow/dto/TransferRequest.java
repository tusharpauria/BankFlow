package com.bankflow.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransferRequest {

    @NotNull
    private Long toAccountId;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    public TransferRequest() {

    }

    public TransferRequest(Long toAccountId, BigDecimal amount) {

        this.toAccountId = toAccountId;
        this.amount = amount;

    }

    public Long getToAccountId() {

        return toAccountId;

    }

    public void setToAccountId(Long toAccountId) {

        this.toAccountId = toAccountId;

    }

    public BigDecimal getAmount() {

        return amount;

    }

    public void setAmount(BigDecimal amount) {

        this.amount = amount;

    }
}