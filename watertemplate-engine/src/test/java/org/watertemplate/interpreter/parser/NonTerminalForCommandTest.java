package org.watertemplate.interpreter.parser;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.watertemplate.interpreter.lexer.TokenFixture.*;

public class NonTerminalForCommandTest {
    @Test
    public void noElse() {
        TokenStream tokenStream = new TokenStream(
            new For(), new PropertyKey("x"), new In(), new PropertyKey("foo"),
            new End()
        );

        assertNotNull(NonTerminal.FOR_COMMAND.buildParseTree(tokenStream));
    }

    @Test
    public void emptyBodies() {
        TokenStream tokenStream = new TokenStream(
            new For(), new PropertyKey("x"), new In(), new PropertyKey("foo"),
            new Else(),
            new End()
        );

        assertNotNull(NonTerminal.FOR_COMMAND.buildParseTree(tokenStream));
    }

    @Test
    public void regular() {
        TokenStream tokenStream = new TokenStream(
            new For(), new PropertyKey("x"), new In(), new PropertyKey("foo"),
            new Text("foo text bar text"),
            new Else(),
            new Text("bar text foo text"),
            new End()
        );

        assertNotNull(NonTerminal.FOR_COMMAND.buildParseTree(tokenStream));
    }
}
