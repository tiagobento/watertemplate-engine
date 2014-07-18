package org.watertemplate.interpreter.parser.exception;

public abstract class ParseException extends RuntimeException {
    public ParseException(final String message) {
        super(message);
    }
}
