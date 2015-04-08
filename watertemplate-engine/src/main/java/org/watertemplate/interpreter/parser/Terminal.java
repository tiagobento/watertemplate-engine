package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.lexer.Token;
import org.watertemplate.interpreter.lexer.TokenType;
import org.watertemplate.interpreter.parser.abs.AbstractSyntaxTree;
import org.watertemplate.interpreter.parser.exception.IncorrectLocationForToken;

enum Terminal implements GrammarSymbol {
    TEXT(new Production.Text()) {
        @Override
        public AbstractSyntaxTree buildAbs(final TokenStream tokenStream) {
            Token current = extractCurrentToken(tokenStream);
            return new AbstractSyntaxTree.Text(current.getValue());
        }
    },
    PROPERTY_KEY(new Production.PropertyKey()) {

        @Override
        public AbstractSyntaxTree buildAbs(final TokenStream tokenStream) {
            Token current = extractCurrentToken(tokenStream);
            return new AbstractSyntaxTree.Id(current.getValue());
        }
    },
    IF, FOR, IN, ELSE, END, ACCESSOR, END_OF_INPUT;

    private final Production production;

    Terminal(Production production) {
        this.production = production;
    }

    Terminal() {
        this.production = new Production.Empty();
    }

    @Override
    public AbstractSyntaxTree buildAbs(TokenStream tokenStream) {
        extractCurrentToken(tokenStream);
        return production.buildAbs(tokenStream);
    }

    Token extractCurrentToken(TokenStream tokenStream) {
        Token current = tokenStream.current();
        if (!isTerminal(current)) {
            throw new IncorrectLocationForToken(getTokenType(), current);
        }

        tokenStream.shift();
        return current;
    }

    private boolean isTerminal(Token currentToken) {
        return getTokenType().equals(currentToken.getType());
    }

    private TokenType getTokenType() {
        return TokenType.valueOf(name());
    }

    public Production getProduction() {
        return production;
    }
}