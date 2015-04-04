package org.watertemplate.interpreter.parser;

import java.util.ArrayList;
import java.util.List;

public class ParseTree {

    private final List<ParseTree> children;

    private final GrammarSymbol grammarSymbol;
    private final String value;

    ParseTree(final GrammarSymbol grammarSymbol) {
        this.grammarSymbol = grammarSymbol;
        this.children = new ArrayList<>();
        this.value = null;
    }

    ParseTree(final Terminal grammarSymbol, final String value) {
        this.grammarSymbol = grammarSymbol;
        this.children = new ArrayList<>();
        this.value = value;
    }

    void addChild(final ParseTree parseTree) {
        children.add(parseTree);
    }
}


