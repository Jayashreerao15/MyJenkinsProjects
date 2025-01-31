package com.itc.exception;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String msg) {
        super(msg);
    }
}