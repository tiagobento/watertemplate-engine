package org.watertemplate.interpreter.parser;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.watertemplate.interpreter.lexer.TokenFixture.*;

public class NonTerminalIfCommandTest {
    @Test
    public void noElse() {
        TokenStream tokenStream = new TokenStream(
            new If(), new PropertyName("x"),
            new End()
        );

        assertTrue(NonTerminal.IF_COMMAND.matches(tokenStream));
    }

    @Test
    public void emptyBodies() {
        TokenStream tokenStream = new TokenStream(
            new If(), new PropertyName("x"),
            new Else(),
            new End()
        );

        assertTrue(NonTerminal.IF_COMMAND.matches(tokenStream));
    }

    @Test
    public void regular() {
        TokenStream tokenStream = new TokenStream(
            new If(), new PropertyName("x"),
            new Text("foo text bar text"),
            new Else(),
            new Text("bar text foo text"),
            new End()
        );

        assertTrue(NonTerminal.IF_COMMAND.matches(tokenStream));
    }

    @Test
    public void missingEnd() {
        TokenStream tokenStream = new TokenStream(
            new If(), new PropertyName("x"),
            new Text("foo text bar text"),
            new Else(),
            new Text("bar text foo text")
        );

        assertFalse(NonTerminal.IF_COMMAND.matches(tokenStream));
    }
}
