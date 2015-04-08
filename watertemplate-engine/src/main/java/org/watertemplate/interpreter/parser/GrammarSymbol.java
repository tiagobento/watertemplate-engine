package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.parser.abs.AbstractSyntaxTree;

interface GrammarSymbol {
    AbstractSyntaxTree buildAbs(TokenStream tokenStream);
}