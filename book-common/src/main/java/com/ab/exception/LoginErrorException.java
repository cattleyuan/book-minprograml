package com.ab.exception;

public class LoginErrorException extends BaseException{
    public LoginErrorException() {
    }

    public LoginErrorException(String message) {
        super(message);
    }
}
