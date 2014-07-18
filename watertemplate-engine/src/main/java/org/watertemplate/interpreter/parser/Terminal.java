package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.lexer.Token;
import org.watertemplate.interpreter.lexer.TokenType;
import org.watertemplate.interpreter.parser.exception.ParseException;

enum Terminal implements ParserSymbol {
    PROPERTY_NAME, IF, FOR, IN, ELSE, END, ACCESSOR, TEXT, END_OF_INPUT;

    @Override
    public ParseTreeNode buildParseTreeFor(final TokenStream tokenStream) {

        if (!isTerminal(tokenStream.current())) {
            throw new ParseException("Incorrect location for " + tokenStream.current());
        }

        tokenStream.shift();
        return new ParseTreeNode(this);
    }

    private boolean isTerminal(Token currentToken) {
        return TokenType.valueOf(name()).equals(currentToken.getType());
    }
}