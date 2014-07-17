package org.watertemplate.interpreter.parser;

import java.util.Arrays;
import java.util.List;

class Production {
    private final List<ParserSymbol> symbols;

    public Production(final ParserSymbol... symbols) {
        this.symbols = Arrays.asList(symbols);
    }

    public boolean matches(final TokenStream tokenStream) {
        tokenStream.save();

        final boolean matches = symbols.stream().allMatch((final ParserSymbol symbol) -> symbol.matches(tokenStream));

        if (!matches) {
            tokenStream.reset();
        }

        return matches;
    }
}
