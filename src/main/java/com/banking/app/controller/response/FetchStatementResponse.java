package com.banking.app.controller.response;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;

@Data
public class FetchStatementResponse implements Serializable {

    private HashSet<AccountEventResponse> statement;
}
