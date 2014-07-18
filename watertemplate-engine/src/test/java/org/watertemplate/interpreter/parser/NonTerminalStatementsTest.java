package org.watertemplate.interpreter.parser;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.watertemplate.interpreter.lexer.TokenFixture.PropertyName;

public class NonTerminalStatementsTest {

    @Test
    public void empty() {
        assertTrue(NonTerminal.STATEMENTS.matches(new TokenStream()));
    }

    @Test
    public void singleStatement() {
        TokenStream tokenStream = new TokenStream(
            new PropertyName("x")
        );

        assertTrue(NonTerminal.STATEMENTS.matches(tokenStream));
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

        assertTrue(NonTerminal.STATEMENTS.matches(tokenStream));
    }
}
