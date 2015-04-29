package org.watertemplate.interpreter.parser;

import java.util.Collections;
import java.util.List;

public class Token {
    public static final Token END_OF_INPUT = new Token("\0", Collections.singletonList(Terminal.END_OF_INPUT));

    private final String value;
    private final List<Terminal> possibleTerminals;

    Token(final String value, final Terminal possibleTerminals) {
        this.possibleTerminals = Collections.singletonList(possibleTerminals);
        this.value = value;
    }

    Token(final String value, final List<Terminal> possibleTerminals) {
        this.possibleTerminals = possibleTerminals;
        this.value = value;
    }

    String getValue() {
        return value;
    }

    boolean canBe(final Terminal terminal) {
        return possibleTerminals.contains(terminal);
    }

    @Override
    public String toString() {
        return "[" + possibleTerminals.stream().map(Enum::toString).reduce((a, b) -> a + "|" + b).get() + "] -> [" + value + "]";
    }
}