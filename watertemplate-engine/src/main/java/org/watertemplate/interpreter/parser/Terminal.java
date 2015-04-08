package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.lexer.Token;
import org.watertemplate.interpreter.lexer.TokenType;
import org.watertemplate.interpreter.parser.exception.IncorrectLocationForToken;

enum Terminal implements GrammarSymbol {
    TEXT {
        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(final Token token) {
            return new AbstractSyntaxTree.Text(token.getValue());
        }
    },
    PROPERTY_KEY {
        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(final Token token) {
            return new AbstractSyntaxTree.Id(token.getValue());
        }
    },

    IF, FOR, IN, ELSE, END, ACCESSOR, END_OF_INPUT;

    AbstractSyntaxTree buildAbstractSyntaxTree(final Token current) {
        return new AbstractSyntaxTree.Empty();
    }

    @Override
    public final AbstractSyntaxTree buildAbstractSyntaxTree(final TokenStream tokenStream) {
        Token current = tokenStream.current();

        if (!isTerminal(current)) {
            throw new IncorrectLocationForToken(getTokenType(), current);
        }

        tokenStream.shift();
        return buildAbstractSyntaxTree(current);
    }

    private boolean isTerminal(final Token currentToken) {
        return getTokenType().equals(currentToken.getType());
    }

    private TokenType getTokenType() {
        return TokenType.valueOf(name());
    }
}