package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.parser.exception.ParseException;

import static org.watertemplate.interpreter.lexer.TokenFixture.Accessor;
import static org.watertemplate.interpreter.lexer.TokenFixture.If;

public class NonTerminalStatementTest {
    @Test(expected = ParseException.class)
    public void incompleteCommand() {
        TokenStream tokenStream = new TokenStream(
            new If()
        );

        NonTerminal.STATEMENT.buildParseTree(tokenStream);
    }

    @Test(expected = ParseException.class)
    public void invalid() {
        TokenStream tokenStream = new TokenStream(
            new Accessor()
        );

        NonTerminal.STATEMENT.buildParseTree(tokenStream);
    }
}
