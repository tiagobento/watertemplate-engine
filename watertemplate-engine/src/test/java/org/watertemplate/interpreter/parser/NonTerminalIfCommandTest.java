package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.parser.exception.IncorrectLocationForToken;
import org.watertemplate.interpreter.parser.exception.NoMoreTokensOnStreamException;

import static org.junit.Assert.assertNotNull;
import static org.watertemplate.interpreter.lexer.TokenFixture.*;

public class NonTerminalIfCommandTest {
    @Test
    public void noElse() {
        TokenStream tokenStream = new TokenStream(
            new If(), new PropertyKey("x"),
            new End()
        );

        assertNotNull(NonTerminal.IF_COMMAND.buildParseTree(tokenStream));
    }

    @Test
    public void emptyBodies() {
        TokenStream tokenStream = new TokenStream(
            new If(), new PropertyKey("x"),
            new Else(),
            new End()
        );

        assertNotNull(NonTerminal.IF_COMMAND.buildParseTree(tokenStream));
    }

    @Test
    public void regular() {
        TokenStream tokenStream = new TokenStream(
            new If(), new PropertyKey("x"),
            new Text("foo text bar text"),
            new Else(),
            new Text("bar text foo text"),
            new End()
        );

        assertNotNull(NonTerminal.IF_COMMAND.buildParseTree(tokenStream));
    }

    @Test
    public void nested() {
        TokenStream tokenStream = new TokenStream(
            new If(), new PropertyKey("x"),
                new If(), new PropertyKey("y"),
                    new Text("nested foo text bar text"),
                new Else(),
                    new If(), new PropertyKey("y"),
                        new Text("else nested foo text bar text"),
                    new Else(),
                        new Text("else nested foo text bar text"),
                    new End(),
                new End(),
            new Else(),
                new Text("bar text foo text"),
            new End()
        );

        assertNotNull(NonTerminal.IF_COMMAND.buildParseTree(tokenStream));
    }

    @Test (expected = IncorrectLocationForToken.class)
    public void invalidNested() {
        TokenStream tokenStream = new TokenStream(
            new If(), new PropertyKey("x"),
                new For(), new PropertyKey("y"), new In(), new PropertyKey("z"),
                    new Text("nested bar text foo text"),
                new Else(),
                    new Text("nested foo text bar text"),
                // new End(), intentionally commented for visualization purpose.
            new Else(),
                new Text("bar text foo text"),
            new End()
        );

        NonTerminal.IF_COMMAND.buildParseTree(tokenStream);
    }

    @Test(expected = NoMoreTokensOnStreamException.class)
    public void missingEnd() {
        TokenStream tokenStream = new TokenStream(
            new If(), new PropertyKey("x"),
                new Text("foo text bar text"),
            new Else(),
                new Text("bar text foo text")
        );

        NonTerminal.IF_COMMAND.buildParseTree(tokenStream);
    }
}
