package org.exceptions.deleteMethodException;

public class DeleteMethodException extends RuntimeException {
    public DeleteMethodException(Throwable throwable, String message) {
        super(message, throwable);
    }
}

