package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.lexer.Token;
import org.watertemplate.interpreter.parser.exception.ParseException;

import java.util.List;

public class RecursiveDescentParser {

    private final TokenStream tokenStream;

    public RecursiveDescentParser(final List<Token> tokens) {
        this.tokenStream = new TokenStream(tokens);
    }

    public ParseTree parse() throws ParseException {
        return NonTerminal.START_SYMBOL.buildParseTreeFor(tokenStream);
    }
}
