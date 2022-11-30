package com.bootcamp.java.customer.web.exceptions;

public class InvalidCustomerTypeException extends Exception {
    private static final long serialVersionUID = 1L;
    public InvalidCustomerTypeException() {
        super("This type of customer is invalid");
    }
}
