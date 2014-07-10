package org.watertemplate.interpreter.lexer;

public enum Keyword {
    FOR("for"),
    IN("in"),
    IF("if"),
    ELSE("else"),
    END("end");

    private final String stringRepresentation;

    Keyword(final String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    public String getStringRepresentation() {
        return stringRepresentation;
    }
}
