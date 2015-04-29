package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.parser.exception.IncorrectLocationForToken;

import static org.junit.Assert.assertNotNull;
import static org.watertemplate.interpreter.parser.TokenFixture.*;

public class NonTerminalIdWithNestedPropertiesTest {
    @Test
    public void singlePropertyKey() {
        TokenStream tokenStream = new TokenStream(
                Wave(), PropertyKey("x"), Wave()
        );

        assertNotNull(NonTerminal.EVALUATION.buildAbstractSyntaxTree(tokenStream));
    }

    @Test
    public void nestedProperties() {
        TokenStream tokenStream = new TokenStream(
                Wave(),
                PropertyKey("x"),
                Accessor(),
                PropertyKey("y"),
                Wave()
        );

        assertNotNull(NonTerminal.EVALUATION.buildAbstractSyntaxTree(tokenStream));
    }

    @Test(expected = IncorrectLocationForToken.class)
    public void doubleAccessor() {
        TokenStream tokenStream = new TokenStream(
                Wave(),
                PropertyKey("x"),
                Accessor(),
                Accessor(),
                PropertyKey("y"),
                Wave()
        );

        NonTerminal.TEMPLATE.buildAbstractSyntaxTree(tokenStream);
    }

    @Test(expected = IncorrectLocationForToken.class)
    public void extraAccessor() {
        TokenStream tokenStream = new TokenStream(
                Wave(),
                PropertyKey("x"),
                Accessor(),
                PropertyKey("y"),
                Accessor(),
                Wave()
        );

        NonTerminal.TEMPLATE.buildAbstractSyntaxTree(tokenStream);
    }
}
