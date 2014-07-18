package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.parser.exception.ParseException;

import static org.junit.Assert.assertNotNull;
import static org.watertemplate.interpreter.lexer.TokenFixture.*;

public class NonTerminalIfCommandTest {
    @Test
    public void noElse() {
        TokenStream tokenStream = new TokenStream(
            new If(), new PropertyName("x"),
            new End()
        );

        assertNotNull(NonTerminal.IF_COMMAND.buildParseTreeFor(tokenStream));
    }

    @Test
    public void emptyBodies() {
        TokenStream tokenStream = new TokenStream(
            new If(), new PropertyName("x"),
            new Else(),
            new End()
        );

        assertNotNull(NonTerminal.IF_COMMAND.buildParseTreeFor(tokenStream));
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

        assertNotNull(NonTerminal.IF_COMMAND.buildParseTreeFor(tokenStream));
    }

    @Test(expected = ParseException.class)
    public void missingEnd() {
        TokenStream tokenStream = new TokenStream(
            new If(), new PropertyName("x"),
            new Text("foo text bar text"),
            new Else(),
            new Text("bar text foo text")
        );

        NonTerminal.IF_COMMAND.buildParseTreeFor(tokenStream);
    }
}
