package com.banking.app.controller.response;

import com.banking.app.operation.Operation;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccountEventResponse implements Serializable {

    private BigDecimal amount;

    private Operation operation;

    private BigDecimal balanceAfterOperation;

    private Date creationDate;
}
