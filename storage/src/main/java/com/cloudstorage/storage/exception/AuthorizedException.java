package com.cloudstorage.storage.exception;

public class AuthorizedException extends RuntimeException {
    public AuthorizedException(String message) {
        super(message);
    }
}
