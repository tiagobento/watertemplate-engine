package org.watertemplate.interpreter.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParseTree {

    private final List<ParseTree> children;

    private final ParseTreeNode node;
    private final String value;

    ParseTree(final Terminal terminal) {
        this(terminal, null);
    }

    ParseTree(final Production node) {
        this.children = new ArrayList<>();
        this.node = node;
        this.value = null;
    }

    ParseTree(final Terminal node, final String value) {
        this.children = new ArrayList<>();
        this.node = node;
        this.value = value;
    }

    void addChild(final ParseTree parseTree) {
        children.add(parseTree);
    }

    String getValue() {
        return value;
    }

    public ParseTreeNode getNode() {
        return node;
    }

    ParseTree child(int i) {
        return children.get(i);
    }

    ParseTree withChildren(ParseTree... parseTrees) {
        children.addAll(Arrays.asList(parseTrees));
        return this;
    }

    public List<ParseTree> getChildren() {
        return children;
    }
}


