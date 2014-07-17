package org.watertemplate.interpreter.lexer;

import org.watertemplate.interpreter.lexer.exception.InvalidTokenException;

import java.util.ArrayList;
import java.util.List;

class Tokens {

    private final List<Token> tokens;
    private StringBuilder currentTokenValue;

    public Tokens() {
        this.tokens = new ArrayList<>();
        this.currentTokenValue = new StringBuilder();
    }

    void acceptFirstIfNotEmpty(final TokenType... types) {
        if (currentTokenValue.length() == 0) {
            return;
        }

        final String tokenValue = currentTokenValue.toString();

        for (final TokenType type : types) {
            if (type.accept(tokenValue)) {
                tokens.add(new Token(tokenValue, type));
                currentTokenValue = new StringBuilder();
                return;
            }
        }

        throw new InvalidTokenException(tokenValue, types[types.length - 1]);
    }

    Tokens append(final Character c) {
        currentTokenValue.append(c);
        return this;
    }

    List<Token> all() {
        return tokens;
    }
}
