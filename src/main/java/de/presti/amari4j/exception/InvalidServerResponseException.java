package de.presti.amari4j.exception;

public class InvalidServerResponseException extends IllegalStateException {
    public InvalidServerResponseException(String message) {
        super(message);
    }
}
