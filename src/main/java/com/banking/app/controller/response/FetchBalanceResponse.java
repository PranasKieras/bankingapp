package com.banking.app.controller.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class FetchBalanceResponse implements Serializable {

    private String email;

    private BigDecimal balance;
}
