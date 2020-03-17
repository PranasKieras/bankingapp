package com.banking.app.controller.request;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class WithdrawalRequest extends AuthenticatedRequest {

    @NotNull(message = "deposit amount needs to be provided")
    @DecimalMin(value = "0.01", message = "amount needs to be more than 0.00")
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
