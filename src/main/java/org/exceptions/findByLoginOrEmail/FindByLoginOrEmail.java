package org.exceptions.findByLoginOrEmail;

import java.sql.SQLException;

public class FindByLoginOrEmail extends RuntimeException {
    public FindByLoginOrEmail(Throwable throwable, String message) {
        super(message, throwable);
    }
}
