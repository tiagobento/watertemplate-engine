package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.lexer.TokenFixture;

import static org.junit.Assert.assertNotNull;

public class NonTerminalForTest {
    @Test
    public void noElse() {
        TokenStream tokenStream = new TokenStream(
                new TokenFixture.For(), new TokenFixture.PropertyKey("x"), new TokenFixture.In(), new TokenFixture.PropertyKey("foo"),
                new TokenFixture.End()
        );

        assertNotNull(NonTerminal.FOR_COMMAND.buildAbstractSyntaxTree(tokenStream));
    }

    @Test
    public void emptyBodies() {
        TokenStream tokenStream = new TokenStream(
                new TokenFixture.For(), new TokenFixture.PropertyKey("x"), new TokenFixture.In(), new TokenFixture.PropertyKey("foo"),
                new TokenFixture.Else(),
                new TokenFixture.End()
        );

        assertNotNull(NonTerminal.FOR_COMMAND.buildAbstractSyntaxTree(tokenStream));
    }

    @Test
    public void regular() {
        TokenStream tokenStream = new TokenStream(
                new TokenFixture.For(), new TokenFixture.PropertyKey("x"), new TokenFixture.In(), new TokenFixture.PropertyKey("foo"),
                new TokenFixture.Text("foo text bar text"),
                new TokenFixture.Else(),
                new TokenFixture.Text("bar text foo text"),
                new TokenFixture.End()
        );

        assertNotNull(NonTerminal.FOR_COMMAND.buildAbstractSyntaxTree(tokenStream));
    }
}
