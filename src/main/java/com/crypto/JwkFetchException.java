package com.crypto;

public class JwkFetchException extends Exception {
    public JwkFetchException(String message) {
        super(message);
    }

    public JwkFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}
