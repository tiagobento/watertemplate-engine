package org.watertemplate.interpreter.parser;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.watertemplate.interpreter.parser.TokenFixture.*;

public class NonTerminalForTest {
    @Test
    public void noElse() {
        TokenStream tokenStream = new TokenStream(
                Wave(), For(), Blank(), PropertyKey("x"), Blank(), In(), Blank(), PropertyKey("foo"), Colon(),
                EndOfBlock()
        );

        assertNotNull(NonTerminal.FOR_COMMAND.buildAbstractSyntaxTree(tokenStream));
    }

    @Test
    public void emptyBodies() {
        TokenStream tokenStream = new TokenStream(
                Wave(), For(), Blank(), PropertyKey("x"), Blank(), In(), Blank(), PropertyKey("foo"), Colon(),
                Else(),
                EndOfBlock()
        );

        assertNotNull(NonTerminal.FOR_COMMAND.buildAbstractSyntaxTree(tokenStream));
    }

    @Test
    public void regular() {
        TokenStream tokenStream = new TokenStream(
                Wave(), For(), Blank(), PropertyKey("x"), Blank(), In(), Blank(), PropertyKey("foo"), Colon(),
                Text("foo text bar text"),
                Else(),
                Text("bar text foo text"),
                EndOfBlock()
        );

        assertNotNull(NonTerminal.FOR_COMMAND.buildAbstractSyntaxTree(tokenStream));
    }
}
