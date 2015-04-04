package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.parser.exception.ParseException;

interface GrammarSymbol {
    ParseTree buildParseTree(final TokenStream tokenStream) throws ParseException;
}
