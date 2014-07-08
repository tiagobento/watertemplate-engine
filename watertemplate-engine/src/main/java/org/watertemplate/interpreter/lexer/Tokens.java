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
        } else if (TokenClass.ID.accept(string)) {
            accept(TokenClass.ID);
        } else {
//            throw new RuntimeException("["+string+"] is not a valid identifier.");
        }
    }

    void accept(final TokenClass clazz) {
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
