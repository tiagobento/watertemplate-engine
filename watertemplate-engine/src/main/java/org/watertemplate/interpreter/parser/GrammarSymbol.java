package org.watertemplate.interpreter.parser;

import org.watertemplate.interpreter.parser.exception.ParseException;

interface GrammarSymbol {
    ParseTreeNode buildParseTreeFor(final TokenStream tokenStream) throws ParseException;
}
