package org.watertemplate.interpreter.lexer;

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

    void accept() {
        final String string = currentTokenValue.toString();

        if (TokenClass.KEYWORD.accept(string)) {
            accept(TokenClass.KEYWORD);
        } else {
            accept(TokenClass.ID);
        }
    }

    void accept(final TokenClass tokenClass) {
        final String tokenValue = currentTokenValue.toString();

        if (!tokenClass.accept(tokenValue)) {
            throw new RuntimeException("[" + tokenValue + "] not accepted as " + tokenClass);
        }

        if (currentTokenValue.length() == 0) {
            return;
        }

        tokens.add(new Token(tokenValue, tokenClass));
        currentTokenValue = new StringBuilder();
    }

    void add(final Character c) {
        currentTokenValue.append(c);
    }

    List<String> values() {
        return tokens.stream()
            .map(Token::getValue)
            .collect(Collectors.toList());
    }

    List<Token> all() {
        return tokens;
    }
}
