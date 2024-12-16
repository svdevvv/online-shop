package org.services.validators.classes.validationResult;

import lombok.Getter;
import org.services.validators.classes.error.Error;

import java.util.ArrayList;
import java.util.List;
@Getter
public class ValidationResult {
    private final List<Error> errors = new ArrayList<>();

    public void add(Error error) {
        this.errors.add(error);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

}





