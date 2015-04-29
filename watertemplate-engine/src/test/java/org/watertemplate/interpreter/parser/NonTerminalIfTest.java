package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.parser.exception.IncorrectLocationForToken;
import org.watertemplate.interpreter.parser.exception.NoMoreTokensOnStreamException;

import static org.junit.Assert.assertNotNull;
import static org.watertemplate.interpreter.parser.TokenFixture.*;

public class NonTerminalIfTest {
    @Test
    public void noElse() {
        TokenStream tokenStream = new TokenStream(
                Wave(), If(), Blank(), PropertyKey("x"), Colon(),
                EndOfBlock()
        );

        assertNotNull(NonTerminal.IF_COMMAND.buildAbstractSyntaxTree(tokenStream));
    }

    @Test
    public void emptyBodies() {
        TokenStream tokenStream = new TokenStream(
                Wave(), If(), Blank(), PropertyKey("x"), Colon(),
                Else(),
                EndOfBlock()
        );

        assertNotNull(NonTerminal.IF_COMMAND.buildAbstractSyntaxTree(tokenStream));
    }

    @Test
    public void regular() {
        TokenStream tokenStream = new TokenStream(
                Wave(), If(), Blank(), PropertyKey("x"), Colon(),
                Text("foo text bar text"),
                Else(),
                Text("bar text foo text"),
                EndOfBlock()
        );

        assertNotNull(NonTerminal.IF_COMMAND.buildAbstractSyntaxTree(tokenStream));
    }

    @Test
    public void nested() {
        TokenStream tokenStream = new TokenStream(
                Wave(), If(), Blank(), PropertyKey("x"), Colon(),
                    Wave(), If(), Blank(), PropertyKey("y"), Colon(),
                        Text("nested foo text bar text"),
                    Else(),
                        Wave(), If(), Blank(), PropertyKey("y"), Colon(),
                            Text("else nested foo text bar text"),
                        Else(),
                            Text("else nested foo text bar text"),
                        EndOfBlock(),
                    EndOfBlock(),
                    Else(),
                        Text("bar text foo text"),
                EndOfBlock()
        );

        assertNotNull(NonTerminal.IF_COMMAND.buildAbstractSyntaxTree(tokenStream));
    }

    @Test (expected = IncorrectLocationForToken.class)
    public void invalidNested() {
        TokenStream tokenStream = new TokenStream(
                Wave(), If(), Blank(), PropertyKey("x"), Colon(),
                    Wave(), If(), Blank(), PropertyKey("y"), Colon(),
                        Text("nested bar text foo text"),
                    Else(),
                        Text("nested foo text bar text"),
                    // new EndOfBlock(), intentionally commented for visualization purpose.
                Else(),
                    Text("bar text foo text"),
                EndOfBlock()
        );

        NonTerminal.IF_COMMAND.buildAbstractSyntaxTree(tokenStream);
    }

    @Test(expected = NoMoreTokensOnStreamException.class)
    public void missingEnd() {
        TokenStream tokenStream = new TokenStream(
                Wave(), If(), Blank(), PropertyKey("x"), Colon(),
                    Text("foo text bar text"),
                Else(),
                    Text("bar text foo text")
                // new EndOfBlock(), intentionally commented for visualization purpose.
        );

        NonTerminal.IF_COMMAND.buildAbstractSyntaxTree(tokenStream);
    }
}
