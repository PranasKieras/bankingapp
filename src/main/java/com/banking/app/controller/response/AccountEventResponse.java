package com.banking.app.controller.response;

import com.banking.app.operation.Operation;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AccountEventResponse implements Serializable {

    private BigDecimal amount;

    private Operation operation;
}
