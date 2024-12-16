package org.services.validators.classes.createUserValidator;

import org.dto.createUserDto.CreateUserDto;
import org.entity.enums.gender.Gender;
import org.services.validators.classes.error.Error;
import org.services.validators.classes.validationResult.ValidationResult;
import org.services.validators.interfaces.validator.Validator;
import org.util.formatters.localDateFormatter.LocalDateFormatter;

public class CreateUserValidator implements Validator<CreateUserDto> {
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidationResult isValid(CreateUserDto object) {
        var validationResult = new ValidationResult();
        if (!LocalDateFormatter.isValid(String.valueOf(object.getBirthday()))) {
            validationResult.add(Error.of("invalid.birthday.date", "Birthday date is invalid"));
        }
        if (object.getGender() == null || Gender.valueOf(object.getGender()) == null){
            validationResult.add(Error.of("invalid.gender", "Gender is invalid"));
        }
        return validationResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
