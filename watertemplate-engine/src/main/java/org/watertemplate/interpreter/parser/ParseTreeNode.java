package org.watertemplate.interpreter.parser;

class ParseTreeNode {
    private final ParserSymbol parserSymbol;

    ParseTreeNode(ParserSymbol parserSymbol) {
        this.parserSymbol = parserSymbol;
    }
}
