package com.banking.app.controller.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper=false)
public class CashOperationRequest extends AuthenticatedRequest{

    @NotNull(message = "deposit amount needs to be provided")
    @DecimalMin(value = "0.01", message = "amount needs to be more than 0.00")
    private BigDecimal amount;

}
