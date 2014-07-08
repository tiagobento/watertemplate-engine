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
        if (Keyword.accept(currentTokenValue.toString())) {
            accept(Token.Clazz.KEYWORD);
        } else {
            accept(Token.Clazz.ID);
        }
    }

    void accept(final Token.Clazz clazz) {
        if (currentTokenValue.length() == 0) {
            return;
        }

        tokens.add(new Token(currentTokenValue.toString(), clazz));
        currentTokenValue = new StringBuilder();
    }

    void add(final Character c) {
        currentTokenValue.append(c);
    }

    public List<String> all() {
        return tokens.stream()
            .map(Token::getValue)
            .collect(Collectors.toList());
    }
}
