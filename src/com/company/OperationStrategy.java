package com.company;

public class OperationStrategy {
    private OperationMethod operationMethod;

    public void setOperationMethod(OperationMethod operationMethod) {
        this.operationMethod = operationMethod;
    }

    public String execute(String text, Mode mode, int cypher) {
        return operationMethod.perform(text, mode, cypher);
    }
}
