package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.parser.exception.IncorrectLocationForToken;
import org.watertemplate.interpreter.parser.exception.NoMoreTokensOnStreamException;

import static org.watertemplate.interpreter.parser.TokenFixture.Accessor;
import static org.watertemplate.interpreter.parser.TokenFixture.If;

public class NonTerminalStatementTest {
    @Test(expected = IncorrectLocationForToken.class)
    public void incompleteCommand() {
        TokenStream tokenStream = new TokenStream(
                If(),
                Token.END_OF_INPUT
        );

        NonTerminal.STATEMENT.buildAbstractSyntaxTree(tokenStream);
    }

    @Test(expected = IncorrectLocationForToken.class)
    public void invalid() {
        TokenStream tokenStream = new TokenStream(
                Accessor(),
                Token.END_OF_INPUT
        );

        NonTerminal.STATEMENT.buildAbstractSyntaxTree(tokenStream);
    }
}
