package wcs.cerebook.controller.exception;

public class postNotFoundException extends RuntimeException {
    public postNotFoundException(String message) {
        super(message);
    }
}
