package org.watertemplate.interpreter.parser;

class ParseTreeNode {
    private final GrammarSymbol grammarSymbol;

    ParseTreeNode(GrammarSymbol grammarSymbol) {
        this.grammarSymbol = grammarSymbol;
    }
}
