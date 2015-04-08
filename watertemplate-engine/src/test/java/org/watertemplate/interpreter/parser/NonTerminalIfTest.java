package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.lexer.TokenFixture;
import org.watertemplate.interpreter.parser.exception.IncorrectLocationForToken;
import org.watertemplate.interpreter.parser.exception.NoMoreTokensOnStreamException;

import static org.junit.Assert.assertNotNull;

public class NonTerminalIfTest {
    @Test
    public void noElse() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.If(), new TokenFixture.PropertyKey("x"),
            new TokenFixture.End()
        );

        assertNotNull(NonTerminal.IF_COMMAND.buildAbstractSyntaxTree(tokenStream));
    }

    @Test
    public void emptyBodies() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.If(), new TokenFixture.PropertyKey("x"),
            new TokenFixture.Else(),
            new TokenFixture.End()
        );

        assertNotNull(NonTerminal.IF_COMMAND.buildAbstractSyntaxTree(tokenStream));
    }

    @Test
    public void regular() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.If(), new TokenFixture.PropertyKey("x"),
            new TokenFixture.Text("foo text bar text"),
            new TokenFixture.Else(),
            new TokenFixture.Text("bar text foo text"),
            new TokenFixture.End()
        );

        assertNotNull(NonTerminal.IF_COMMAND.buildAbstractSyntaxTree(tokenStream));
    }

    @Test
    public void nested() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.If(), new TokenFixture.PropertyKey("x"),
                new TokenFixture.If(), new TokenFixture.PropertyKey("y"),
                    new TokenFixture.Text("nested foo text bar text"),
                new TokenFixture.Else(),
                    new TokenFixture.If(), new TokenFixture.PropertyKey("y"),
                        new TokenFixture.Text("else nested foo text bar text"),
                    new TokenFixture.Else(),
                        new TokenFixture.Text("else nested foo text bar text"),
                    new TokenFixture.End(),
                new TokenFixture.End(),
            new TokenFixture.Else(),
                new TokenFixture.Text("bar text foo text"),
            new TokenFixture.End()
        );

        assertNotNull(NonTerminal.IF_COMMAND.buildAbstractSyntaxTree(tokenStream));
    }

    @Test (expected = IncorrectLocationForToken.class)
    public void invalidNested() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.If(), new TokenFixture.PropertyKey("x"),
                new TokenFixture.For(), new TokenFixture.PropertyKey("y"), new TokenFixture.In(), new TokenFixture.PropertyKey("z"),
                    new TokenFixture.Text("nested bar text foo text"),
                new TokenFixture.Else(),
                    new TokenFixture.Text("nested foo text bar text"),
                // new End(), intentionally commented for visualization purpose.
            new TokenFixture.Else(),
                new TokenFixture.Text("bar text foo text"),
            new TokenFixture.End()
        );

        NonTerminal.IF_COMMAND.buildAbstractSyntaxTree(tokenStream);
    }

    @Test(expected = NoMoreTokensOnStreamException.class)
    public void missingEnd() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.If(), new TokenFixture.PropertyKey("x"),
                new TokenFixture.Text("foo text bar text"),
            new TokenFixture.Else(),
                new TokenFixture.Text("bar text foo text")
        );

        NonTerminal.IF_COMMAND.buildAbstractSyntaxTree(tokenStream);
    }
}
