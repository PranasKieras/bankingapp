package com.banking.app.controller.response;

import java.io.Serializable;
import java.math.BigDecimal;

public class FetchBalanceResponse implements Serializable {

    private String email;

    private BigDecimal balance;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
