package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.lexer.Token;
import org.watertemplate.interpreter.lexer.TokenType;
import org.watertemplate.interpreter.parser.exception.IncorrectLocationForToken;

enum Terminal implements GrammarSymbol {
    TEXT(new Production.Text()),
    PROPERTY_KEY(new Production.PropertyKey()),
    IF, FOR, IN, ELSE, END, ACCESSOR, END_OF_INPUT;

    private final Production production;

    Terminal() {
        this.production = new Production.Empty();
    }

    Terminal(final Production production) {
        this.production = production;
    }

    @Override
    public final ParseTree buildParseTree(final TokenStream tokenStream) {

        Token current = tokenStream.current();
        if (!isTerminal(current)) {
            throw new IncorrectLocationForToken(getTokenType(), current);
        }

        tokenStream.shift();
        return new ParseTree(production, current.getValue());
    }

    private boolean isTerminal(Token currentToken) {
        return getTokenType().equals(currentToken.getType());
    }

    private TokenType getTokenType() {
        return TokenType.valueOf(name());
    }

    Production getProduction() {
        return production;
    }
}