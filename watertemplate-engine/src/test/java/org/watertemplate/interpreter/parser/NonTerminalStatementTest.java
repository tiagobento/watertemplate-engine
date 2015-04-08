package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.lexer.TokenFixture;
import org.watertemplate.interpreter.parser.exception.ParseException;

public class NonTerminalStatementTest {
    @Test(expected = ParseException.class)
    public void incompleteCommand() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.If()
        );

        NonTerminal.STATEMENT.buildAbstractSyntaxTree(tokenStream);
    }

    @Test(expected = ParseException.class)
    public void invalid() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.Accessor()
        );

        NonTerminal.STATEMENT.buildAbstractSyntaxTree(tokenStream);
    }
}
