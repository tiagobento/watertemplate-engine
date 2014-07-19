package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.lexer.TokenFixture;

import static org.junit.Assert.assertNotNull;

public class NonTerminalStatementsTest {

    @Test
    public void empty() {
        assertNotNull(NonTerminal.STATEMENTS.buildParseTree(new TokenStream()));
    }

    @Test
    public void singleStatement() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.PropertyKey("x")
        );

        assertNotNull(NonTerminal.STATEMENTS.buildParseTree(tokenStream) != null);
    }

    @Test
    public void multipleStatements() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.PropertyKey("x"),
            new TokenFixture.PropertyKey("y"),
            new TokenFixture.PropertyKey("z"),
            new TokenFixture.PropertyKey("w"),
            new TokenFixture.PropertyKey("foo"),
            new TokenFixture.PropertyKey("bar")
        );

        assertNotNull(NonTerminal.STATEMENTS.buildParseTree(tokenStream) != null);
    }
}
