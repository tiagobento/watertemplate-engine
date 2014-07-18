package org.watertemplate.interpreter.parser;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.watertemplate.interpreter.lexer.TokenFixture.PropertyName;

public class NonTerminalStatementsTest {

    @Test
    public void empty() {
        assertNotNull(NonTerminal.STATEMENTS.buildParseTreeFor(new TokenStream()));
    }

    @Test
    public void singleStatement() {
        TokenStream tokenStream = new TokenStream(
            new PropertyName("x")
        );

        assertNotNull(NonTerminal.STATEMENTS.buildParseTreeFor(tokenStream) != null);
    }

    @Test
    public void multipleStatements() {
        TokenStream tokenStream = new TokenStream(
            new PropertyName("x"),
            new PropertyName("y"),
            new PropertyName("z"),
            new PropertyName("w"),
            new PropertyName("foo"),
            new PropertyName("bar")
        );

        assertNotNull(NonTerminal.STATEMENTS.buildParseTreeFor(tokenStream) != null);
    }
}
