package org.watertemplate.interpreter.parser;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.watertemplate.interpreter.lexer.TokenFixture.*;

public class NonTerminalForCommandTest {
    @Test
    public void noElse() {
        TokenStream tokenStream = new TokenStream(
            new For(), new PropertyName("x"), new In(), new PropertyName("foo"),
            new End()
        );

        assertNotNull(NonTerminal.FOR_COMMAND.buildParseTreeFor(tokenStream));
    }

    @Test
    public void emptyBodies() {
        TokenStream tokenStream = new TokenStream(
            new For(), new PropertyName("x"), new In(), new PropertyName("foo"),
            new Else(),
            new End()
        );

        assertNotNull(NonTerminal.FOR_COMMAND.buildParseTreeFor(tokenStream));
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

        assertNotNull(NonTerminal.FOR_COMMAND.buildParseTreeFor(tokenStream));
    }
}
