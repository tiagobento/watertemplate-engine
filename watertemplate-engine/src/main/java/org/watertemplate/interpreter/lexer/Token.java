package org.watertemplate.interpreter.lexer;

public class Token {
    private final String value;
    private final Clazz clazz;

    public Token(final String value, final Clazz clazz) {
        this.value = value;
        this.clazz = clazz;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return clazz + "|" + value;
    }

    static enum Clazz {
        TEXT, KEYWORD, ACCESSOR, ID
    }
}
