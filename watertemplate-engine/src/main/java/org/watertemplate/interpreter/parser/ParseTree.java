package org.watertemplate.interpreter.parser;

import java.util.ArrayList;
import java.util.List;

class ParseTree extends ParseTreeNode {
    private final List<ParseTreeNode> children;

    ParseTree(final ParserSymbol parserSymbol) {
        super(parserSymbol);
        this.children = new ArrayList<>();
    }

    void addChild(final ParseTreeNode parseTree) {
        children.add(parseTree);
    }
}


