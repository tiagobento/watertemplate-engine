package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.lexer.Token;

import java.util.List;

public class RecursiveDescentParser {

    private final TokenStream tokenStream;

    public RecursiveDescentParser(final List<Token> tokens) {
        this.tokenStream = new TokenStream(tokens);
    }

    public void parse() {
        if (tokenStream.hasAny()) {
            NonTerminal.START_SYMBOL.matches(tokenStream);
        }
    }
}
