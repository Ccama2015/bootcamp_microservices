package com.bootcamp.java.customer.web.exceptions;

public class CustomerException extends Exception {
    private static final long serialVersionUID = 1L;
    public CustomerException(String msg) {
        super(msg);
    }
}
