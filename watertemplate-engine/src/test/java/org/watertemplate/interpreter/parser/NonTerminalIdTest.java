package org.watertemplate.interpreter.parser;

import junit.framework.Assert;
import org.junit.Test;
import org.watertemplate.interpreter.parser.exception.IncorrectLocationForToken;
import org.watertemplate.interpreter.parser.exception.NoMoreTokensOnStreamException;
import org.watertemplate.interpreter.parser.exception.ParseException;

import static org.junit.Assert.assertNotNull;
import static org.watertemplate.interpreter.lexer.TokenFixture.Accessor;
import static org.watertemplate.interpreter.lexer.TokenFixture.PropertyName;

public class NonTerminalIdTest {
    @Test
    public void singlePropertyName() {
        TokenStream tokenStream = new TokenStream(
            new PropertyName("x")
        );

        assertNotNull(NonTerminal.ID.buildParseTreeFor(tokenStream));
    }

    @Test
    public void nestedProperties() {
        TokenStream tokenStream = new TokenStream(
            new PropertyName("x"),
            new Accessor(),
            new PropertyName("y")
        );

        assertNotNull(NonTerminal.ID.buildParseTreeFor(tokenStream));
    }

    @Test(expected = IncorrectLocationForToken.class)
    public void doubleAccessor() {
        TokenStream tokenStream = new TokenStream(
            new PropertyName("x"),
            new Accessor(),
            new Accessor(),
            new PropertyName("y")
        );

        NonTerminal.ID.buildParseTreeFor(tokenStream);
    }

    @Test(expected = NoMoreTokensOnStreamException.class)
    public void extraAccessor() {
        TokenStream tokenStream = new TokenStream(
            new PropertyName("x"),
            new Accessor(),
            new PropertyName("y"),
            new Accessor()
        );

        NonTerminal.ID.buildParseTreeFor(tokenStream);
    }
}
