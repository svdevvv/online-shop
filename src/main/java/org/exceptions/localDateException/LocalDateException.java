package org.exceptions.localDateException;

import java.time.DateTimeException;

public class LocalDateException extends RuntimeException {
    public LocalDateException(Throwable throwable, String message) {
        super(message, throwable);
    }
}
