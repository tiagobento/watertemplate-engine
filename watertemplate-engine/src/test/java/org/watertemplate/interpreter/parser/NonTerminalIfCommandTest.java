package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.lexer.TokenFixture;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NonTerminalIfCommandTest {
    @Test
    public void noElse() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.If(), new TokenFixture.PropertyName("x"),
            new TokenFixture.End()
        );

        assertTrue(NonTerminal.IF_COMMAND.matches(tokenStream));
    }

    @Test
    public void emptyBodies() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.If(), new TokenFixture.PropertyName("x"),
            new TokenFixture.Else(),
            new TokenFixture.End()
        );

        assertTrue(NonTerminal.IF_COMMAND.matches(tokenStream));
    }

    @Test
    public void regular() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.If(), new TokenFixture.PropertyName("x"),
            new TokenFixture.Text("foo text bar text"),
            new TokenFixture.Else(),
            new TokenFixture.Text("bar text foo text"),
            new TokenFixture.End()
        );

        assertTrue(NonTerminal.IF_COMMAND.matches(tokenStream));
    }

    @Test
    public void missingEnd() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.If(), new TokenFixture.PropertyName("x"),
            new TokenFixture.Text("foo text bar text"),
            new TokenFixture.Else(),
            new TokenFixture.Text("bar text foo text")
        );

        assertFalse(NonTerminal.IF_COMMAND.matches(tokenStream));
    }
}
