package com.jelisavacluka554.rmt_common.communication;

import java.io.Serializable;

/**
 *
 * @author luka
 */
public class Request implements Serializable{

    private Operation operation;
    
    private Object argument;

    public Request(Operation operation, Object argument) {
        this.operation = operation;
        this.argument = argument;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Object getArgument() {
        return argument;
    }

    public void setArgument(Object argument) {
        this.argument = argument;
    }
    
}
