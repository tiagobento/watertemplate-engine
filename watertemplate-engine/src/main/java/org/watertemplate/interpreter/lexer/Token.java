package org.watertemplate.interpreter.lexer;

public class Token {
    public static final Token END_OF_INPUT = new Token("eoi", TokenType.END_OF_INPUT);

    private final String value;
    private final TokenType type;

    Token(final String value, final TokenType type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "[" + value + "] (at i:j)";
    }

}
