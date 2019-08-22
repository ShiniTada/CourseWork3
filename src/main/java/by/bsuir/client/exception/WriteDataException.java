package by.bsuir.client.exception;

public class WriteDataException extends Exception {
    public WriteDataException() {
    }

    public WriteDataException(String message) {
        super(message);
    }

    public WriteDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public WriteDataException(Throwable cause) {
        super(cause);
    }
}
