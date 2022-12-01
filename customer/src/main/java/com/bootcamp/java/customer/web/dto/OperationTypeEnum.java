package com.bootcamp.java.customer.web.dto;

public enum OperationTypeEnum {
    DEPOSIT("1"), REMOVE("2");
    private String value;
    OperationTypeEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}