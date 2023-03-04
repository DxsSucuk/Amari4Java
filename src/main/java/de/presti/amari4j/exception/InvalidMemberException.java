package de.presti.amari4j.exception;

/**
 * An Exception representing the API Error in case of an unknown Member.
 */
public class InvalidMemberException extends IllegalArgumentException {

    /**
     * Constructor to allow a custom message.
     * @param message the custom message.
     */
    public InvalidMemberException(String message) {
        super(message);
    }
}
