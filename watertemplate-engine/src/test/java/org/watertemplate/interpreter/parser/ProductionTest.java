package org.watertemplate.interpreter.parser;

import org.junit.Assert;
import org.junit.Test;

import static org.watertemplate.interpreter.lexer.TokenFixture.*;

public class ProductionTest {

    @Test
    public void matchesOnlyTerminals() {
        final TokenStream tokenStream = new TokenStream(
            new Else(),
            new Accessor(),
            new PropertyName("x"),
            new End()
        );

        final Production productionThatDoesNotMatch = new Production(Terminal.IF);
        Assert.assertFalse(productionThatDoesNotMatch.matches(tokenStream));

        final Production productionThatMatches = new Production(
            Terminal.ELSE,
            Terminal.ACCESSOR,
            Terminal.PROPERTY_NAME,
            Terminal.END);

        Assert.assertTrue(productionThatMatches.matches(tokenStream));
    }

    @Test
    public void matchesTerminalsAndNonTerminals() {
        final TokenStream tokenStream = new TokenStream(
            new If(),
            new PropertyName("x"),
            new Text("foo text"),
            new PropertyName("y"),
            new Accessor(),
            new PropertyName("z"),
            new End()
        );

        final Production production = new Production(
            Terminal.IF,
            NonTerminal.ID,
            Terminal.TEXT,
            NonTerminal.ID_EVALUATION,
            Terminal.END
        );

        Assert.assertTrue(production.matches(tokenStream));

    }
}
