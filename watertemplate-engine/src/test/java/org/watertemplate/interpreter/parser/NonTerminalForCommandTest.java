package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.lexer.TokenFixture;

import static org.junit.Assert.assertTrue;

public class NonTerminalForCommandTest {
    @Test
    public void noElse() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.For(), new TokenFixture.PropertyName("x"), new TokenFixture.In(), new TokenFixture.PropertyName("foo"),
            new TokenFixture.End()
        );

        assertTrue(NonTerminal.FOR_COMMAND.matches(tokenStream));
    }

    @Test
    public void emptyBodies() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.For(), new TokenFixture.PropertyName("x"), new TokenFixture.In(), new TokenFixture.PropertyName("foo"),
            new TokenFixture.Else(),
            new TokenFixture.End()
        );

        assertTrue(NonTerminal.FOR_COMMAND.matches(tokenStream));
    }

    @Test
    public void regular() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.For(), new TokenFixture.PropertyName("x"), new TokenFixture.In(), new TokenFixture.PropertyName("foo"),
            new TokenFixture.Text("foo text bar text"),
            new TokenFixture.Else(),
            new TokenFixture.Text("bar text foo text"),
            new TokenFixture.End()
        );

        assertTrue(NonTerminal.FOR_COMMAND.matches(tokenStream));
    }
}
