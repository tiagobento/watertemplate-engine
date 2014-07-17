package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.lexer.TokenFixture;

import static org.junit.Assert.assertTrue;

public class NonTerminalStatementsTest {

    @Test
    public void empty() {
        assertTrue(NonTerminal.STATEMENTS.matches(new TokenStream()));
    }

    @Test
    public void singleStatement() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.PropertyName("x")
        );

        assertTrue(NonTerminal.STATEMENTS.matches(tokenStream));
    }

    @Test
    public void multipleStatements() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.PropertyName("x"),
            new TokenFixture.PropertyName("y"),
            new TokenFixture.PropertyName("z"),
            new TokenFixture.PropertyName("w"),
            new TokenFixture.PropertyName("foo"),
            new TokenFixture.PropertyName("bar")
        );

        assertTrue(NonTerminal.STATEMENTS.matches(tokenStream));
    }
}
