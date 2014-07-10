package org.watertemplate.interpreter.lexer;

public enum Keyword {
    FOR, IN, IF, ELSE, END;

    public String getStringRepresentation() {
        return this.name().toLowerCase();
    }
}
