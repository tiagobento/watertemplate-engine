package org.watertemplate.interpreter.parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.watertemplate.interpreter.lexer.TokenType;

import static org.watertemplate.interpreter.lexer.TokenFixture.*;

public class TokenStreamTest {

    private TokenStream tokenStream;

    @Before
    public void before() {
        tokenStream = new TokenStream(
            new If(),
            new PropertyKey("foo"),
            new Else(),
            new End());
    }

    @Test
    public void hasAny() {
        Assert.assertFalse(new TokenStream().hasAny());
        Assert.assertTrue(tokenStream.hasAny());
    }

    @Test
    public void currentAndShift() {
        assertCurrentIsOfType(TokenType.IF);
        tokenStream.shift();
        assertCurrentIsOfType(TokenType.PROPERTY_KEY);
        tokenStream.shift();
        assertCurrentIsOfType(TokenType.ELSE);
    }

    @Test
    public void saveAndReset() {
        int save = tokenStream.getCurrentTokenPosition();
        assertCurrentIsOfType(TokenType.IF);
        tokenStream.shift();
        tokenStream.shift();
        tokenStream.shift();
        assertCurrentIsNotOfType(TokenType.IF);
        tokenStream.reset(save);
        assertCurrentIsOfType(TokenType.IF);
    }

    @Test
    public void remaining() {
        Assert.assertEquals(4, tokenStream.remaining());
        tokenStream.shift();
        tokenStream.shift();
        Assert.assertEquals(2, tokenStream.remaining());
        tokenStream.shift();
        tokenStream.shift();
        Assert.assertEquals(0, tokenStream.remaining());
    }

    private void assertCurrentIsOfType(final TokenType tokenType) {
        Assert.assertEquals(tokenType, current());
    }

    private void assertCurrentIsNotOfType(final TokenType tokenType) {
        Assert.assertNotEquals(tokenType, current());
    }

    private TokenType current() {
        return tokenStream.current().getType();
    }
}
