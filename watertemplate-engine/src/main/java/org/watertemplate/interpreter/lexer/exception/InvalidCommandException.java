package org.watertemplate.interpreter.lexer.exception;

import org.watertemplate.interpreter.lexer.TokenClass;

public class InvalidCommandException extends RuntimeException {

    public InvalidCommandException(final String tokenValue, final TokenClass tokenClass) {
        super("[" + tokenValue + "] is not a valid " + tokenClass.name().toLowerCase());
    }
}
