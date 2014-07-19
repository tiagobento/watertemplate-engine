package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.parser.exception.ParseException;

import static org.junit.Assert.assertNotNull;
import static org.watertemplate.interpreter.lexer.TokenFixture.*;

public class ProductionTest {

    @Test(expected = ParseException.class)
    public void invalidProductionWithOnlyTerminals() {
        final TokenStream tokenStream = new TokenStream(
            new Else(),
            new Accessor(),
            new PropertyKey("x"),
            new End()
        );

        new Production(null, Terminal.IF).buildParseTree(tokenStream);
    }

    @Test
    public void validWithOnlyTerminals() {
        final TokenStream tokenStream = new TokenStream(
            new Else(),
            new Accessor(),
            new PropertyKey("x"),
            new End()
        );

        final Production productionThatMatches = new Production(null,
            Terminal.ELSE,
            Terminal.ACCESSOR,
            Terminal.PROPERTY_KEY,
            Terminal.END);

        assertNotNull(productionThatMatches.buildParseTree(tokenStream));
    }

    @Test
    public void validWithTerminalsAndNonTerminals() {
        final TokenStream tokenStream = new TokenStream(
            new If(),
            new PropertyKey("x"),
            new Text("foo text"),
            new PropertyKey("y"),
            new Accessor(),
            new PropertyKey("z"),
            new End()
        );

        final Production production = new Production(null,
            Terminal.IF,
            NonTerminal.ID,
            Terminal.TEXT,
            NonTerminal.ID,
            Terminal.END
        );

        assertNotNull(production.buildParseTree(tokenStream));

    }
}
