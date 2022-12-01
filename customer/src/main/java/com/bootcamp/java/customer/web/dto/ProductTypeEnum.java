package com.bootcamp.java.customer.web.dto;

public enum ProductTypeEnum {
    SAVING_ACCOUNT("1"),
    CURRENT_ACCOUNT("2"),
    FIXED_TERM_ACCOUNT("3"),
    PERSONNEL_CREDIT("4"),
    BUSINESS_CREDIT("5"),
    CREDIT_CARD("6");
    private String value;
    ProductTypeEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}