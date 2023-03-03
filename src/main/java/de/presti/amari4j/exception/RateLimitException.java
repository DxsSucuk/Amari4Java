package de.presti.amari4j.exception;

public class RateLimitException extends IllegalAccessException {
    public RateLimitException(String message) {
        super(message);
    }
}
