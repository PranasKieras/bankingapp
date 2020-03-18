package com.banking.app.controller.response;

import java.io.Serializable;
import java.util.Set;

public class FetchStatementResponse implements Serializable {

    Set<AccountEventResponse> statement;

    public Set<AccountEventResponse> getStatement() {
        return statement;
    }

    public void setStatement(Set<AccountEventResponse> statement) {
        this.statement = statement;
    }
}
