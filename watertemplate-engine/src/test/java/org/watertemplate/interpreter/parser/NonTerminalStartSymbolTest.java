package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.lexer.Token;
import org.watertemplate.interpreter.lexer.TokenFixture;
import org.watertemplate.interpreter.parser.exception.ParseException;

import static org.junit.Assert.assertNotNull;
import static org.watertemplate.interpreter.lexer.TokenFixture.Text;

public class NonTerminalStartSymbolTest {

    @Test
    public void onlyEndOfInput() {
        TokenStream tokenStream = new TokenStream(
            Token.END_OF_INPUT
        );

        assertNotNull(NonTerminal.START_SYMBOL.buildParseTree(tokenStream));
    }

    @Test(expected = ParseException.class)
    public void missingEndOfInput() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.PropertyKey("x"),
            new Text("a text"),
            new TokenFixture.PropertyKey("y"),
            new Text("another text")
        );

        NonTerminal.START_SYMBOL.buildParseTree(tokenStream);
    }

    @Test
    public void regular() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.PropertyKey("x"),
            new Text("a text"),
            new TokenFixture.PropertyKey("y"),
            new Text("another text"),
            Token.END_OF_INPUT
        );

        assertNotNull(NonTerminal.START_SYMBOL.buildParseTree(tokenStream));
    }
}
