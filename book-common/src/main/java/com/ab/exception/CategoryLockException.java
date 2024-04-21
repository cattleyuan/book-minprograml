package com.ab.exception;

/**
 * @author cattleYuan
 * @date 2024/1/31
 */
public class CategoryLockException extends BaseException{
    public CategoryLockException() {
    }

    public CategoryLockException(String message) {
        super(message);
    }
}
