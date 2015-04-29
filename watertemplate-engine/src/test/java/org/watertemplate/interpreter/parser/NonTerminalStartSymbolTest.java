package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.parser.exception.NoMoreTokensOnStreamException;

import static org.junit.Assert.assertNotNull;
import static org.watertemplate.interpreter.parser.TokenFixture.*;

public class NonTerminalStartSymbolTest {

    @Test
    public void onlyEndOfInput() {
        TokenStream tokenStream = new TokenStream(
                Token.END_OF_INPUT
        );

        assertNotNull(NonTerminal.TEMPLATE.buildAbstractSyntaxTree(tokenStream));
    }

    @Test(expected = NoMoreTokensOnStreamException.class)
    public void missingEndOfInput() {
        TokenStream tokenStream = new TokenStream(
                Wave(), PropertyKey("x"), Wave(),
                Text("a text"),
                Wave(), PropertyKey("y"), Wave(),
                Text("another text")
        );

        NonTerminal.TEMPLATE.buildAbstractSyntaxTree(tokenStream);
    }

    @Test
    public void regular() {
        TokenStream tokenStream = new TokenStream(
                Wave(), PropertyKey("x"), Wave(),
                Text("a text"),
                Wave(), PropertyKey("y"), Wave(),
                Text("another text"),
                Token.END_OF_INPUT
        );

        assertNotNull(NonTerminal.TEMPLATE.buildAbstractSyntaxTree(tokenStream));
    }
}
