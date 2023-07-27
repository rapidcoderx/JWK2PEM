package com.crypto;

public class PemFileWriteException extends Exception {
    public PemFileWriteException(String message) {
        super(message);
    }

    public PemFileWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
