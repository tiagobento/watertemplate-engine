package org.watertemplate.interpreter.parser.exception;

import org.watertemplate.interpreter.lexer.Token;

public class IncorrectLocationForToken extends ParseException {
    public IncorrectLocationForToken(final Token token) {
        super("Incorrect location for " + token);
    }
}
