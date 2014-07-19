package org.watertemplate.interpreter.parser.exception;

import org.watertemplate.interpreter.lexer.Token;
import org.watertemplate.interpreter.lexer.TokenType;

public class IncorrectLocationForToken extends ParseException {
    public IncorrectLocationForToken(final TokenType expected, final Token token) {
        super("Expected " + expected + " but found " + token);
    }
}
