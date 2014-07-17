package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.lexer.Token;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.watertemplate.interpreter.lexer.TokenFixture.PropertyName;
import static org.watertemplate.interpreter.lexer.TokenFixture.Text;

public class NonTerminalStartSymbolTest {

    @Test
    public void onlyEndOfInput() {
        TokenStream tokenStream = new TokenStream(
            Token.END_OF_INPUT
        );

        assertTrue(NonTerminal.START_SYMBOL.matches(tokenStream));
    }

    @Test
    public void missingEndOfInput() {
        TokenStream tokenStream = new TokenStream(
            new PropertyName("x"),
            new Text("a text"),
            new PropertyName("y"),
            new Text("another text")
        );

        assertFalse(NonTerminal.START_SYMBOL.matches(tokenStream));
    }

    @Test
    public void regular() {
        TokenStream tokenStream = new TokenStream(
            new PropertyName("x"),
            new Text("a text"),
            new PropertyName("y"),
            new Text("another text"),
            Token.END_OF_INPUT
        );

        assertTrue(NonTerminal.START_SYMBOL.matches(tokenStream));
    }
}
