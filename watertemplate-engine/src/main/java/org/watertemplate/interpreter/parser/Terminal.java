package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.lexer.Token;
import org.watertemplate.interpreter.lexer.TokenType;
import org.watertemplate.interpreter.parser.exception.IncorrectLocationForToken;

enum Terminal implements GrammarSymbol {
    PROPERTY_KEY, IF, FOR, IN, ELSE, END, ACCESSOR, TEXT, END_OF_INPUT;

    @Override
    public ParseTreeNode buildParseTree(final TokenStream tokenStream) {

        if (!isTerminal(tokenStream.current())) {
            throw new IncorrectLocationForToken(getTokenType(), tokenStream.current());
        }

        tokenStream.shift();
        return new ParseTreeNode(this);
    }

    private boolean isTerminal(Token currentToken) {
        return getTokenType().equals(currentToken.getType());
    }

    private TokenType getTokenType() {
        return TokenType.valueOf(name());
    }
}