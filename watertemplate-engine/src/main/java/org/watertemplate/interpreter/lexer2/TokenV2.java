package org.watertemplate.interpreter.lexer2;

import java.util.Collections;
import java.util.List;

class TokenV2 {
    public static final TokenV2 END_OF_INPUT = new TokenV2("\0", Collections.singletonList(TerminalV2.END_OF_INPUT));

    private final String value;
    private final List<TerminalV2> possibleTerminals;

    public TokenV2(final String value, final List<TerminalV2> possibleTerminals) {
        this.possibleTerminals = possibleTerminals;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "[" + possibleTerminals.stream().map(Enum::toString).reduce((a, b) -> a + "|" + b).get() + "] -> [" + value + "]";
    }
}