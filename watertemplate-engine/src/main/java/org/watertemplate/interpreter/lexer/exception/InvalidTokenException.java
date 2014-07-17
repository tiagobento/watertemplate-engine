package org.watertemplate.interpreter.lexer.exception;

import org.watertemplate.interpreter.lexer.TokenType;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(final String tokenValue, final TokenType type) {
        super("[" + tokenValue + "] is not a valid " + type.name().toLowerCase());
    }
}
