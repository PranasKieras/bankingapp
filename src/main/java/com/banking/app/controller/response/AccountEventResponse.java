package com.banking.app.controller.response;

import com.banking.app.operation.Operation;

import java.math.BigDecimal;

public class AccountEventResponse {

    private Long id;

    private BigDecimal amount;

    private Operation operation;

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

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
