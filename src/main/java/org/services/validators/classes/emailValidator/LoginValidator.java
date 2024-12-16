package org.services.validators.classes.emailValidator;

import java.util.regex.Pattern;

public class LoginValidator {
    private static final LoginValidator INSTANCE = new LoginValidator();
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._]+@(mail|gmail|yandex)+\\.(com|ru|ua|org)$";

    public boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        } else {
            return Pattern.matches(EMAIL_REGEX, email);
        }
    }
    public static LoginValidator getInstance() {
        return INSTANCE;
    }

}

