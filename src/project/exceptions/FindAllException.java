package project.exceptions;

public class FindAllException extends RuntimeException {
    public FindAllException(Throwable throwable,String message) {
        super(message,throwable);
    }
}
