package de.presti.amari4j.exception;

/**
 * An Exception representing the API Error in case of receiving a Rate-limit
 */
public class RateLimitException extends IllegalAccessException {

    /**
     * Constructor to allow a custom message.
     * @param message the custom message.
     */
    public RateLimitException(String message) {
        super(message);
    }
}
