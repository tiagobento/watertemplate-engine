package org.watertemplate.interpreter.parser;

interface GrammarSymbol {
    AbstractSyntaxTree buildAbstractSyntaxTree(TokenStream tokenStream);
}