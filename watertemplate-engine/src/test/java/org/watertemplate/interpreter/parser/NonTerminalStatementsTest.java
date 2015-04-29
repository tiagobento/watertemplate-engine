package org.watertemplate.interpreter.parser;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.watertemplate.interpreter.parser.TokenFixture.PropertyKey;

public class NonTerminalStatementsTest {

    @Test
    public void empty() {
        assertNotNull(NonTerminal.STATEMENTS.buildAbstractSyntaxTree(new TokenStream()));
    }

    @Test
    public void singleStatement() {
        TokenStream tokenStream = new TokenStream(
                PropertyKey("x")
        );

        assertNotNull(NonTerminal.STATEMENTS.buildAbstractSyntaxTree(tokenStream));
    }

    @Test
    public void multipleStatements() {
        TokenStream tokenStream = new TokenStream(
                PropertyKey("x"),
                PropertyKey("y"),
                PropertyKey("z"),
                PropertyKey("w"),
                PropertyKey("foo"),
                PropertyKey("bar")
        );

        assertNotNull(NonTerminal.STATEMENTS.buildAbstractSyntaxTree(tokenStream));
    }
}
