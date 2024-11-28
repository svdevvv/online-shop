package org.exceptions.loadPropertiesException;

public class LoadPropertiesException extends RuntimeException {
    public LoadPropertiesException(Throwable throwable, String message) {
        super(message, throwable);
    }
}