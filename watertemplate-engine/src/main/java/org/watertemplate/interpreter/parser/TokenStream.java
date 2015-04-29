package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.parser.exception.NoMoreTokensOnStreamException;

import java.util.Arrays;
import java.util.List;

class TokenStream {
    private final List<Token> tokens;
    private int currentTokenPosition;

    TokenStream(final Token... tokens) {
        this(Arrays.asList(tokens));
    }

    TokenStream(final List<Token> tokens) {
        this.tokens = tokens;
        currentTokenPosition = 0;
    }

    boolean hasAny() {
        return !tokens.isEmpty();
    }

    public Token current() throws NoMoreTokensOnStreamException {
        if (currentTokenPosition >= tokens.size()) {
            throw new NoMoreTokensOnStreamException();
        }

        return tokens.get(currentTokenPosition);
    }

    public void reset(int save) {
        currentTokenPosition = save;
    }

    public void shift() {
        currentTokenPosition++;
    }

    public int remaining() {
        return tokens.size() - currentTokenPosition;
    }

    public int getCurrentTokenPosition() {
        return currentTokenPosition;
    }
}
