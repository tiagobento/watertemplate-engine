package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.parser.exception.IncorrectLocationForToken;
import org.watertemplate.interpreter.parser.exception.NoMoreTokensOnStreamException;
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

    @Test
    public void nested() {
        TokenStream tokenStream = new TokenStream(
            new If(), new PropertyName("x"),
                new If(), new PropertyName("y"),
                    new Text("nested foo text bar text"),
                new Else(),
                    new If(), new PropertyName("y"),
                        new Text("else nested foo text bar text"),
                    new Else(),
                        new Text("else nested foo text bar text"),
                    new End(),
                new End(),
            new Else(),
                new Text("bar text foo text"),
            new End()
        );

        assertNotNull(NonTerminal.IF_COMMAND.buildParseTreeFor(tokenStream));
    }

//    @Test(expected = NoMoreTokensOnStreamException.class)
    public void invalidNested() {
        TokenStream tokenStream = new TokenStream(
            new If(), new PropertyName("x"),
                new If(), new PropertyName("y"),
                    new Text("nested foo text bar text"),
                new Else(),
                    new If(), new PropertyName("y"),
                        new Text("else nested foo text bar text"),
                    new Else(),
                        new Text("else nested foo text bar text"),
                    new End(),
                new End(),
            new Else(),
                new Text("bar text foo text"),
            new End()
        );

        NonTerminal.IF_COMMAND.buildParseTreeFor(tokenStream);
    }

    @Test(expected = NoMoreTokensOnStreamException.class)
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
