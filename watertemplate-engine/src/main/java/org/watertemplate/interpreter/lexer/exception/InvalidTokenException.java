package org.watertemplate.interpreter.lexer.exception;

import org.watertemplate.interpreter.lexer.TokenClass;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(final String tokenValue, final TokenClass tokenClass) {
        super("[" + tokenValue + "] is not a valid " + tokenClass.name().toLowerCase());
    }
}
