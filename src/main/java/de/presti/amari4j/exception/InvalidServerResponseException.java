package de.presti.amari4j.exception;

/**
 * An Exception representing an unhandled server response
 */
public class InvalidServerResponseException extends IllegalStateException {

    /**
     * Constructor to allow a custom message.
     * @param message the custom message.
     */
    public InvalidServerResponseException(String message) {
        super(message);
    }
}
