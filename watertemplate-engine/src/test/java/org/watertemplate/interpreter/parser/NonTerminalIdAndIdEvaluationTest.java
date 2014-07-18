package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.parser.exception.ParseException;

import static org.junit.Assert.assertNotNull;
import static org.watertemplate.interpreter.lexer.TokenFixture.Accessor;
import static org.watertemplate.interpreter.lexer.TokenFixture.PropertyName;

public class NonTerminalIdAndIdEvaluationTest {
    @Test
    public void singlePropertyName() {
        TokenStream tokenStream = new TokenStream(
            new PropertyName("x")
        );

        assertNotNull(NonTerminal.ID.buildParseTreeFor(tokenStream));
    }

    @Test
    public void nestedProperties() {
        TokenStream tokenStream = new TokenStream(
            new PropertyName("x"),
            new Accessor(),
            new PropertyName("y")
        );

        assertNotNull(NonTerminal.ID.buildParseTreeFor(tokenStream));
    }

    @Test(expected = ParseException.class)
    public void doubleAccessor() {
        TokenStream tokenStream = new TokenStream(
            new PropertyName("x"),
            new Accessor(),
            new Accessor(),
            new PropertyName("y")
        );

        NonTerminal.ID.buildParseTreeFor(tokenStream);
    }

    @Test(expected = ParseException.class)
    public void extraAccessor() {
        TokenStream tokenStream = new TokenStream(
            new PropertyName("x"),
            new Accessor(),
            new PropertyName("y"),
            new Accessor()
        );

        NonTerminal.ID.buildParseTreeFor(tokenStream);
    }
}
