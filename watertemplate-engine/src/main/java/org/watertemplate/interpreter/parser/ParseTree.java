package org.watertemplate.interpreter.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParseTree {

    private final List<ParseTree> children;

    private final Production production;
    private final String value;

    ParseTree(final Terminal terminal) {
        this(terminal.getProduction());
    }

    ParseTree(final Terminal terminal, final String value) {
        this(terminal.getProduction(), value);
    }

    ParseTree(final Production production) {
        this(production, null);
    }

    ParseTree(final Production production, final String value) {
        this.children = new ArrayList<>();
        this.production = production;
        this.value = value;
    }

    void addChild(final ParseTree parseTree) {
        children.add(parseTree);
    }

    String getValue() {
        return value;
    }

    public Production getProduction() {
        return production;
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


