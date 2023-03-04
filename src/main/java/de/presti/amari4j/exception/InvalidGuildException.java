package de.presti.amari4j.exception;

/**
 * An Exception representing the API Error in case of an unknown Guild.
 */
public class InvalidGuildException extends IllegalArgumentException {

    /**
     * Constructor to allow a custom message.
     * @param message the custom message.
     */
    public InvalidGuildException(String message) {
        super(message);
    }
}
