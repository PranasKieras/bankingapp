package com.banking.app.operation;

import java.math.BigDecimal;
import java.util.function.BinaryOperator;

public enum Operation {

    DEPOSIT(BigDecimal::add),
    WITHDRAWAL(BigDecimal::subtract);

    private final BinaryOperator<BigDecimal> operator;

    Operation(BinaryOperator<BigDecimal> operator){
        this.operator = operator;
    }

    public BigDecimal apply(BigDecimal balance, BigDecimal amount){
        return operator.apply(balance, amount);
    }
}
