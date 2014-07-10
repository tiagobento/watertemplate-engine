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

    void acceptFirstIfNotEmpty(final TokenClass... tokenClasses) {
        if (currentTokenValue.length() == 0) {
            return;
        }

        final String tokenValue = currentTokenValue.toString();

        for (final TokenClass tokenClass : tokenClasses) {
            if (tokenClass.accept(tokenValue)) {
                tokens.add(new Token(tokenValue, tokenClass));
                currentTokenValue = new StringBuilder();
                return;
            }
        }

        throw new InvalidTokenException(tokenValue, tokenClasses[tokenClasses.length - 1]);
    }

    Tokens append(final Character c) {
        currentTokenValue.append(c);
        return this;
    }

    List<Token> all() {
        return tokens;
    }
}
