package org.watertemplate.interpreter.lexer;

public class TokenFixture {
    public static class If extends Token {
        public If() {
            super(Keyword.IF.getStringRepresentation(), TokenType.IF);
        }
    }

    public static class Else extends Token {
        public Else() {
            super(Keyword.ELSE.getStringRepresentation(), TokenType.ELSE);
        }
    }

    public static class In extends Token {
        public In() {
            super(Keyword.IN.getStringRepresentation(), TokenType.IN);
        }
    }

    public static class End extends Token {
        public End() {
            super(Keyword.END.getStringRepresentation(), TokenType.END);
        }
    }

    public static class For extends Token {
        public For() {
            super(Keyword.FOR.getStringRepresentation(), TokenType.FOR);
        }
    }

    public static class Text extends Token {
        public Text(final String text) {
            super(text, TokenType.TEXT);
        }
    }

    public static class PropertyKey extends Token {
        public PropertyKey(final String propertyKey) {
            super(propertyKey, TokenType.PROPERTY_KEY);
        }
    }

    public static class Accessor extends Token {
        public Accessor() {
            super(LexerSymbol.ACCESSOR + "", TokenType.ACCESSOR);
        }
    }
}
