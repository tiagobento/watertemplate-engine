package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.lexer.TokenFixture;
import org.watertemplate.interpreter.parser.exception.IncorrectLocationForToken;
import org.watertemplate.interpreter.parser.exception.NoMoreTokensOnStreamException;
import org.watertemplate.interpreter.parser.exception.ParseException;

public class NonTerminalStatementTest {
    @Test(expected = NoMoreTokensOnStreamException.class)
    public void incompleteCommand() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.If()
        );

        NonTerminal.STATEMENT.buildAbstractSyntaxTree(tokenStream);
    }

    @Test(expected = IncorrectLocationForToken.class)
    public void invalid() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.Accessor()
        );

        NonTerminal.STATEMENT.buildAbstractSyntaxTree(tokenStream);
    }
}
