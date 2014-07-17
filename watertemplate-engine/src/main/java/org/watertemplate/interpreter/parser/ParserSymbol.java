package org.watertemplate.interpreter.parser;

interface ParserSymbol {
    boolean matches(final TokenStream tokenStream);
}
