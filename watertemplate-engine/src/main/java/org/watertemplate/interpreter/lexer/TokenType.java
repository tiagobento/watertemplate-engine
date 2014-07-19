package org.watertemplate.interpreter.lexer;

import org.watertemplate.interpreter.lexer.Keyword;
import org.watertemplate.interpreter.lexer.LexerSymbol;

import java.util.regex.Pattern;

public enum TokenType {
    PROPERTY_KEY {
        @Override
        public boolean accept(final String string) {
            return PROPERTY_NAME_PATTERN.matcher(string).matches();
        }
    },
    IF {
        @Override
        public boolean accept(String string) {
            return Keyword.IF.getStringRepresentation().equals(string);
        }
    },
    FOR {
        @Override
        public boolean accept(String string) {
            return Keyword.FOR.getStringRepresentation().equals(string);
        }
    },
    ELSE {
        @Override
        public boolean accept(String string) {
            return Keyword.ELSE.getStringRepresentation().equals(string);
        }
    },
    IN {
        @Override
        public boolean accept(String string) {
            return Keyword.IN.getStringRepresentation().equals(string);
        }
    },
    END {
        @Override
        public boolean accept(String string) {
            return Keyword.END.getStringRepresentation().equals(string);
        }
    },
    ACCESSOR {
        @Override
        public boolean accept(String string) {
            return ("" + LexerSymbol.ACCESSOR).equals(string);
        }
    },
    TEXT,
    END_OF_INPUT;

    private final static Pattern PROPERTY_NAME_PATTERN = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*$");

    public boolean accept(final String string) {
        return true;
    }
}
