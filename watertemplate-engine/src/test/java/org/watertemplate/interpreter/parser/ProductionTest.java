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
            new PropertyName("x"),
            new End()
        );

        new Production(null, Terminal.IF).buildParseTreeFor(tokenStream);
    }

    @Test
    public void validWithOnlyTerminals() {
        final TokenStream tokenStream = new TokenStream(
            new Else(),
            new Accessor(),
            new PropertyName("x"),
            new End()
        );

        final Production productionThatMatches = new Production(null,
            Terminal.ELSE,
            Terminal.ACCESSOR,
            Terminal.PROPERTY_NAME,
            Terminal.END);

        assertNotNull(productionThatMatches.buildParseTreeFor(tokenStream));
    }

    @Test
    public void validWithTerminalsAndNonTerminals() {
        final TokenStream tokenStream = new TokenStream(
            new If(),
            new PropertyName("x"),
            new Text("foo text"),
            new PropertyName("y"),
            new Accessor(),
            new PropertyName("z"),
            new End()
        );

        final Production production = new Production(null,
            Terminal.IF,
            NonTerminal.ID,
            Terminal.TEXT,
            NonTerminal.ID,
            Terminal.END
        );

        assertNotNull(production.buildParseTreeFor(tokenStream));

    }
}
