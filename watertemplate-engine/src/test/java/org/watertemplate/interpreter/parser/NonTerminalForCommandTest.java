package org.watertemplate.interpreter.parser;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.watertemplate.interpreter.lexer.TokenFixture.*;

public class NonTerminalForCommandTest {
    @Test
    public void noElse() {
        TokenStream tokenStream = new TokenStream(
            new For(), new PropertyName("x"), new In(), new PropertyName("foo"),
            new End()
        );

        assertTrue(NonTerminal.FOR_COMMAND.matches(tokenStream));
    }

    @Test
    public void emptyBodies() {
        TokenStream tokenStream = new TokenStream(
            new For(), new PropertyName("x"), new In(), new PropertyName("foo"),
            new Else(),
            new End()
        );

        assertTrue(NonTerminal.FOR_COMMAND.matches(tokenStream));
    }

    @Test
    public void regular() {
        TokenStream tokenStream = new TokenStream(
            new For(), new PropertyName("x"), new In(), new PropertyName("foo"),
            new Text("foo text bar text"),
            new Else(),
            new Text("bar text foo text"),
            new End()
        );

        assertTrue(NonTerminal.FOR_COMMAND.matches(tokenStream));
    }
}
