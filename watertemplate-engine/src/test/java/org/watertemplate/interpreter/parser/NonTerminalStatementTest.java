package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.lexer.TokenFixture;

import static org.junit.Assert.assertFalse;

public class NonTerminalStatementTest {
    @Test
    public void incompleteCommand() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.If()
        );

        assertFalse(NonTerminal.STATEMENT.matches(tokenStream));
    }

    @Test
    public void invalid() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.Accessor()
        );

        assertFalse(NonTerminal.STATEMENT.matches(tokenStream));
    }
}
