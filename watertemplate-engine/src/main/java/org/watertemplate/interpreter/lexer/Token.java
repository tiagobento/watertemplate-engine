package org.watertemplate.interpreter.lexer;

public class Token {
    private final String value;
    private final TokenClass tokenClass;

    public Token(final String value, final TokenClass tokenClass) {
        this.value = value;
        this.tokenClass = tokenClass;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return tokenClass + "|" + value;
    }

}
