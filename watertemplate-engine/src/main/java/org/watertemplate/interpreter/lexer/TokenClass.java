package org.watertemplate.interpreter.lexer;

import java.util.Arrays;
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
    SYMBOL,
    TEXT;

    private final static Pattern ID_PATTERN = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*$");
    private final static Set<String> KEYWORDS = new HashSet<>();

    static {
        Arrays.asList(Keyword.values())
            .stream()
            .forEach(k -> KEYWORDS.add(k.getStringRepresentation()));
    }

    public boolean accept(final String string) {
        return true;
    }
}
