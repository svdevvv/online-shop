package project.exceptions;

public class DeleteMethodException extends RuntimeException {
    public DeleteMethodException(Throwable throwable, String message) {
        super(message,throwable);
    }
}
