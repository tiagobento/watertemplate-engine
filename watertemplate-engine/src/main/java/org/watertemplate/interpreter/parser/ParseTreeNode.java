package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.parser.abs.AbstractSyntaxTree;

public interface ParseTreeNode {
    AbstractSyntaxTree buildAbstractSyntaxTree(ParseTree parseTree);
}
