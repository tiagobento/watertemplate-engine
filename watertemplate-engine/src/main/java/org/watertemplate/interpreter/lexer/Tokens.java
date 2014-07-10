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

    void accept() {
        final String string = currentTokenValue.toString();

        if (TokenClass.KEYWORD.accept(string)) {
            accept(TokenClass.KEYWORD);
        } else {
            accept(TokenClass.IDENTIFIER);
        }
    }

    void accept(final TokenClass tokenClass) {
        if (currentTokenValue.length() == 0) {
            return;
        }

        final String tokenValue = currentTokenValue.toString();

        if (!tokenClass.accept(tokenValue)) {
            throw new InvalidTokenException(tokenValue, tokenClass);
        }

        tokens.add(new Token(tokenValue, tokenClass));
        currentTokenValue = new StringBuilder();
    }

    Tokens add(final Character c) {
        currentTokenValue.append(c);
        return this;
    }

    List<Token> all() {
        return tokens;
    }
}
