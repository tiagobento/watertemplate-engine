package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.lexer.Token;
import org.watertemplate.interpreter.lexer.TokenType;
import org.watertemplate.interpreter.parser.abs.AbstractSyntaxTree;
import org.watertemplate.interpreter.parser.exception.IncorrectLocationForToken;

enum Terminal implements GrammarSymbol, ParseTreeNode {
    PROPERTY_KEY {
        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(final ParseTree parseTree) {
            return new AbstractSyntaxTree.Id(parseTree.getValue());
        }
    },
    TEXT {
        @Override
        public AbstractSyntaxTree buildAbstractSyntaxTree(final ParseTree parseTree) {
            return new AbstractSyntaxTree.Text(parseTree.getValue());
        }
    },

    IF, FOR, IN, ELSE, END, ACCESSOR, END_OF_INPUT;

    @Override
    public final ParseTree buildParseTree(final TokenStream tokenStream) {

        Token current = tokenStream.current();
        if (!isTerminal(current)) {
            throw new IncorrectLocationForToken(getTokenType(), current);
        }

        tokenStream.shift();
        return new ParseTree(this, current.getValue());
    }

    @Override
    public AbstractSyntaxTree buildAbstractSyntaxTree(ParseTree parseTree) {
        return new AbstractSyntaxTree.Empty();
    }

    private boolean isTerminal(Token currentToken) {
        return getTokenType().equals(currentToken.getType());
    }

    private TokenType getTokenType() {
        return TokenType.valueOf(name());
    }
}