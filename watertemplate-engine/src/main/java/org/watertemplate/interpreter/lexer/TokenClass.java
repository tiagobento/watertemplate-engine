package org.watertemplate.interpreter.lexer;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public enum TokenClass {
    KEYWORD {
        @Override
        public boolean accept(final String string) {
            return KEYWORDS.contains(string);
        }
    },
    IDENTIFIER {
        @Override
        public boolean accept(final String string) {
            return ID_PATTERN.matcher(string).matches();
        }
    },
    ACCESSOR,
    TEXT;

    final static Pattern ID_PATTERN = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*$");
    final static Set<String> KEYWORDS = new HashSet<>();

    static {
        KEYWORDS.add("for");
        KEYWORDS.add("in");
        KEYWORDS.add("if");
        KEYWORDS.add("else");
        KEYWORDS.add("end");
    }

    public boolean accept(final String string) {
        return true;
    }
}
