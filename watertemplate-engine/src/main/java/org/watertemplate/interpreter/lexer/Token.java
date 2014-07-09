package org.watertemplate.interpreter.lexer;

public class Token {
    private final String value;
    private final TokenClass tokenClass;
    private final int lineNumber;
    private final int columnAccepted;

    public Token(final String value, final TokenClass tokenClass, int lineNumber, int columnAccepted) {
        this.value = value;
        this.tokenClass = tokenClass;

        this.lineNumber = lineNumber;
        this.columnAccepted = columnAccepted;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return tokenClass + "|" + value;
    }

}
