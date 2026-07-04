package com.exceptions;

public class CartItemNotExistException extends RuntimeException {
    public CartItemNotExistException(String message) {
        super(message);
    }
}
