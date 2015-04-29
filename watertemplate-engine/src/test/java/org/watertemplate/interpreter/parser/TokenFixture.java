package org.watertemplate.interpreter.parser;

public class TokenFixture {
    public static Token For() {
        return new Token(Keywords.FOR, Terminal.FOR);
    }

    public static Token If() {
        return new Token(Keywords.IF, Terminal.IF);
    }

    public static Token EndOfBlock() {
        return new Token(Keywords.END_OF_BLOCK, Terminal.END_OF_BLOCK);
    }

    public static Token In() {
        return new Token(Keywords.IN, Terminal.IN);
    }

    public static Token Else() {
        return new Token(Keywords.ELSE, Terminal.ELSE);
    }

    public static Token Text(final String x) {
        return new Token(x, Terminal.TEXT);
    }

    public static Token PropertyKey(final String x) {
        return new Token(x, Terminal.PROPERTY_KEY);
    }

    public static Token Wave() {
        return new Token(Keywords.WAVE, Terminal.WAVE);
    }

    public static Token Colon() {
        return new Token(Keywords.COLON, Terminal.COLON);
    }

    public static Token Blank() {
        return new Token(" ", Terminal.BLANK);
    }

    public static Token Accessor() {
        return new Token(Keywords.ACCESSOR, Terminal.ACCESSOR);
    }
}
