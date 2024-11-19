package project.exceptions;

public class DAOException extends RuntimeException {
    public DAOException(Throwable throwable, String message) {
        super(message,throwable);
    }
}
