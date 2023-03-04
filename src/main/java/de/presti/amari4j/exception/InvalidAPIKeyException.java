package de.presti.amari4j.exception;

/**
 * An Exception representing the API Error in case of a wrong API-Key
 */
public class InvalidAPIKeyException extends IllegalAccessException {

    /**
     * Constructor to allow a custom message.
     * @param message the custom message.
     */
    public InvalidAPIKeyException(String message) {
        super(message);
    }
}
