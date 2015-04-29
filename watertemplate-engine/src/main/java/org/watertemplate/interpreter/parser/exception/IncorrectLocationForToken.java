package org.watertemplate.interpreter.parser.exception;

public class IncorrectLocationForToken extends ParseException {
    public IncorrectLocationForToken(final Object expected, final Object token) {
        super("Expected [" + expected + "] but found " + token);
    }
}
