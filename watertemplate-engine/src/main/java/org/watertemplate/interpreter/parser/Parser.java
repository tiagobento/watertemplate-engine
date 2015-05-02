package org.watertemplate.interpreter.parser;

import java.util.List;

public class Parser {
    public AbstractSyntaxTree parse(final List<Token> tokens) {
        return NonTerminal.TEMPLATE.buildAbstractSyntaxTree(new TokenStream(tokens));
    }
}
