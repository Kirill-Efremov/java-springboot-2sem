package itis.spring.exceptions;

public class FileSizeException extends RuntimeException {
    public FileSizeException(String message) {
        super(message);
    }
}