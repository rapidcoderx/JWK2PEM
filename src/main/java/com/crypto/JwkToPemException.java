package com.crypto;

public class JwkToPemException extends Exception {
    public JwkToPemException(String message) {
        super(message);
    }

    public JwkToPemException(String message, Throwable cause) {
        super(message, cause);
    }
}
