package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.lexer.Token;
import org.watertemplate.interpreter.parser.abs.AbstractSyntaxTree;

import java.util.List;

public class RecursiveDescentParser {

    private final TokenStream tokenStream;

    public RecursiveDescentParser(final List<Token> tokens) {
        this.tokenStream = new TokenStream(tokens);
    }

    public AbstractSyntaxTree buildAbs() {
        return NonTerminal.START_SYMBOL.buildAbs(tokenStream);
    }
}
