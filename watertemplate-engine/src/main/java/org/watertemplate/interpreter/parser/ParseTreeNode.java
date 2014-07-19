package org.watertemplate.interpreter.parser;

class ParseTreeNode {
    private final GrammarSymbol grammarSymbol;

    ParseTreeNode(final GrammarSymbol grammarSymbol) {
        this.grammarSymbol = grammarSymbol;
    }
}
