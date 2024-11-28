package org.exceptions.findByIdException;

public class FindByIdException extends RuntimeException {
    public FindByIdException(Throwable throwable,String message) {
        super(message,throwable);
    }
}
