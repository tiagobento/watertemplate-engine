package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.parser.exception.ParseException;

import java.util.Arrays;
import java.util.List;

class Production implements GrammarSymbol {
    private final NonTerminal nonTerminal;
    private final List<GrammarSymbol> symbols;

    public Production(final NonTerminal nonTerminal, final GrammarSymbol... symbols) {
        this.nonTerminal = nonTerminal;
        this.symbols = Arrays.asList(symbols);
    }

    @Override
    public ParseTree buildParseTreeFor(final TokenStream tokenStream) throws ParseException {
        final ParseTree parseTree = new ParseTree(nonTerminal);
        tokenStream.save();

        try {
            for (GrammarSymbol symbol : symbols) {
                parseTree.addChild(symbol.buildParseTreeFor(tokenStream));
            }
        } catch (ParseException e) {
            tokenStream.reset();
            throw e;
        }

        return parseTree;
    }
}
