package org.watertemplate.interpreter.lexer;

import java.util.HashMap;
import java.util.Map;

enum Keyword {
    IF("if"),
    IN("in"),
    FOR("for"),
    ELSE("else");

    private static final Map<String, Keyword> keywords = new HashMap<>();

    static {
        for (Keyword keyword : values()) keywords.put(keyword.string, keyword);
    }

    private final String string;

    Keyword(final String string) {
        this.string = string;
    }

    public static boolean accept(final String string) {
        return keywords.containsKey(string);
    }
}
