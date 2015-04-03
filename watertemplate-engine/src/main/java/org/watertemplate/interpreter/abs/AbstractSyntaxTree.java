package org.watertemplate.interpreter.abs;

import org.watertemplate.interpreter.parser.ParseTree;

public class AbstractSyntaxTree {
    private final ParseTree parseTree;

    public AbstractSyntaxTree(final ParseTree parseTree) {
        this.parseTree = parseTree;
    }
}
