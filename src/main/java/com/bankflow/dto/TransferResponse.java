package com.bankflow.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferResponse {

    private String message;
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public TransferResponse() {

    }

    public TransferResponse(String message, Long fromAccountId, Long toAccountId, BigDecimal amount, LocalDateTime timestamp) {

        this.message = message;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.timestamp = timestamp;

    }

    public String getMessage() {

        return message;

    }

    public void setMessage(String message) {

        this.message = message;

    }

    public Long getFromAccountId() {

        return fromAccountId;

    }

    public void setFromAccountId(Long fromAccountId) {

        this.fromAccountId = fromAccountId;

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

    public LocalDateTime getTimestamp() {

        return timestamp;

    }

    public void setTimestamp(LocalDateTime timestamp) {

        this.timestamp = timestamp;

    }
}