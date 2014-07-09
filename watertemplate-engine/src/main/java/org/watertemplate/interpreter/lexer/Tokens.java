package org.watertemplate.interpreter.lexer;

import org.watertemplate.interpreter.lexer.exception.InvalidCommandException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Tokens {

    private final List<Token> tokens;
    private StringBuilder currentTokenValue;

    public Tokens() {
        this.tokens = new ArrayList<>();
        this.currentTokenValue = new StringBuilder();
    }

    void accept(int lineNumber, int columnAccepted) {
        final String string = currentTokenValue.toString();

        if (TokenClass.KEYWORD.accept(string)) {
            accept(TokenClass.KEYWORD, lineNumber, columnAccepted);
        } else {
            accept(TokenClass.ID, lineNumber, columnAccepted);
        }
    }

    void accept(final TokenClass tokenClass, int lineNumber, int columnAccepted) {
        if (currentTokenValue.length() == 0) {
            return;
        }

        final String tokenValue = currentTokenValue.toString();

        if (!tokenClass.accept(tokenValue)) {
            throw new InvalidCommandException(tokenValue, tokenClass);
        }

        tokens.add(new Token(tokenValue, tokenClass, lineNumber, columnAccepted));
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
