package org.watertemplate.interpreter.parser;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.watertemplate.interpreter.lexer.TokenFixture.Accessor;
import static org.watertemplate.interpreter.lexer.TokenFixture.If;

public class NonTerminalStatementTest {
    @Test
    public void incompleteCommand() {
        TokenStream tokenStream = new TokenStream(
            new If()
        );

        assertFalse(NonTerminal.STATEMENT.matches(tokenStream));
    }

    @Test
    public void invalid() {
        TokenStream tokenStream = new TokenStream(
            new Accessor()
        );

        assertFalse(NonTerminal.STATEMENT.matches(tokenStream));
    }
}
