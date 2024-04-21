package com.ab.exception;

/**
 * @author cattleYuan
 * @date 2024/4/1
 */
public class NoneTokenException extends RuntimeException{
    public NoneTokenException() {
    }

    public NoneTokenException(String message) {
        super(message);
    }
}
