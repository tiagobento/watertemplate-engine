package org.watertemplate.interpreter.parser;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.watertemplate.interpreter.lexer.TokenFixture.Accessor;
import static org.watertemplate.interpreter.lexer.TokenFixture.PropertyName;

public class NonTerminalIdAndIdEvaluationTest {
    @Test
    public void singlePropertyName() {
        TokenStream tokenStream = new TokenStream(
            new PropertyName("x")
        );

        assertTrue(NonTerminal.ID.matches(tokenStream));
    }

    @Test
    public void nestedProperties() {
        TokenStream tokenStream = new TokenStream(
            new PropertyName("x"),
            new Accessor(),
            new PropertyName("y")
        );

        assertTrue(NonTerminal.ID.matches(tokenStream));
    }

    @Test
    public void doubleAccessor() {
        TokenStream tokenStream = new TokenStream(
            new PropertyName("x"),
            new Accessor(),
            new Accessor(),
            new PropertyName("y")
        );

        assertFalse(NonTerminal.ID.matches(tokenStream));
    }

    @Test
    public void extraAccessor() {
        TokenStream tokenStream = new TokenStream(
            new PropertyName("x"),
            new Accessor(),
            new PropertyName("y"),
            new Accessor()
        );

        assertFalse(NonTerminal.ID.matches(tokenStream));
    }
}
