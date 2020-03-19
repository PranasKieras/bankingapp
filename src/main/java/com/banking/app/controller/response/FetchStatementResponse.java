package com.banking.app.controller.response;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashSet;

@Data
public class FetchStatementResponse implements Serializable {

    private LinkedHashSet<AccountEventResponse> statement;
}
