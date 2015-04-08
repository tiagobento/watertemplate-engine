package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.interpreter.lexer.TokenFixture;
import org.watertemplate.interpreter.parser.exception.IncorrectLocationForToken;

import static org.junit.Assert.assertNotNull;

public class NonTerminalIdWithNestedPropertiesTest {
    @Test
    public void singlePropertyKey() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.PropertyKey("x")
        );

        assertNotNull(NonTerminal.ID.buildAbstractSyntaxTree(tokenStream));
    }

    @Test
    public void nestedProperties() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.PropertyKey("x"),
            new TokenFixture.Accessor(),
            new TokenFixture.PropertyKey("y")
        );

        assertNotNull(NonTerminal.ID.buildAbstractSyntaxTree(tokenStream));
    }

    @Test (expected = IncorrectLocationForToken.class)
    public void doubleAccessor() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.PropertyKey("x"),
            new TokenFixture.Accessor(),
            new TokenFixture.Accessor(),
            new TokenFixture.PropertyKey("y")
        );

        NonTerminal.START_SYMBOL.buildAbstractSyntaxTree(tokenStream);
    }

    @Test (expected = IncorrectLocationForToken.class)
    public void extraAccessor() {
        TokenStream tokenStream = new TokenStream(
            new TokenFixture.PropertyKey("x"),
            new TokenFixture.Accessor(),
            new TokenFixture.PropertyKey("y"),
            new TokenFixture.Accessor()
        );

        NonTerminal.START_SYMBOL.buildAbstractSyntaxTree(tokenStream);
    }
}
