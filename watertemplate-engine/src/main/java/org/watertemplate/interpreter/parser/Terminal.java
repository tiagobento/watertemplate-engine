package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.lexer.Token;
import org.watertemplate.interpreter.lexer.TokenType;
import org.watertemplate.interpreter.parser.exception.NoMoreTokensOnStreamException;

enum Terminal implements ParserSymbol {
    PROPERTY_NAME, IF, FOR, IN, ELSE, END, ACCESSOR, TEXT, END_OF_INPUT;

    public boolean matches(final TokenStream tokenStream) throws NoMoreTokensOnStreamException {
        try {
            final Token current = tokenStream.current();
            final TokenType tokenTypeForThisTerminal = TokenType.valueOf(name());

            final boolean accept = tokenTypeForThisTerminal.equals(current.getType());

            if (accept) {
                tokenStream.shift();
            }

            return accept;
        } catch (NoMoreTokensOnStreamException e) {
            return false;
        }
    }
}
