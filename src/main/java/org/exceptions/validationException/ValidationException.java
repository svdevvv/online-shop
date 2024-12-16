package org.exceptions.validationException;

import lombok.Getter;
import org.services.validators.classes.error.Error;

import java.util.List;


@Getter
public class ValidationException extends RuntimeException {
    private final List<Error> errors;

    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }
}

