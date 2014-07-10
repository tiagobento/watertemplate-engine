package org.watertemplate.interpreter.lexer.exception;

public class IncompleteTokenException extends RuntimeException {
    public IncompleteTokenException(final int i, final int j) {
        super("Incomplete token at " + i + ":" + j);
    }
}
