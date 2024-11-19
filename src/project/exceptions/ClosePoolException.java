package project.exceptions;

public class ClosePoolException extends RuntimeException {
    public ClosePoolException(Throwable throwable, String message) {
        super(message,throwable);
    }
}
