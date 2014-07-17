package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.lexer.TokenFixture;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NonTerminalIdAndIdEvaluationTest {
    @Test
    public void singlePropertyName() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.PropertyName("x")
        );

        assertTrue(NonTerminal.ID.matches(tokenStream));
    }

    @Test
    public void nestedProperties() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.PropertyName("x"),
            new TokenFixture.Accessor(),
            new TokenFixture.PropertyName("y")
        );

        assertTrue(NonTerminal.ID.matches(tokenStream));
    }

    @Test
    public void doubleAccessor() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.PropertyName("x"),
            new TokenFixture.Accessor(),
            new TokenFixture.Accessor(),
            new TokenFixture.PropertyName("y")
        );

        assertFalse(NonTerminal.ID.matches(tokenStream));
    }

    @Test
    public void extraAccessor() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.PropertyName("x"),
            new TokenFixture.Accessor(),
            new TokenFixture.PropertyName("y"),
            new TokenFixture.Accessor()
        );

        assertFalse(NonTerminal.ID.matches(tokenStream));
    }
}
