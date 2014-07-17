package org.watertemplate.interpreter.lexer;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TokensTest {

    @Test
    public void acceptKeyword() {
        Tokens tokens = tokensWithString(Keyword.ELSE.getStringRepresentation());
        tokens.acceptFirstIfNotEmpty(TokenType.ELSE);
        assertTokensHasOnlyOne(TokenType.ELSE, tokens);
    }

    @Test
    public void acceptPropertyName() {
        Tokens tokens = tokensWithString("foobar");
        tokens.acceptFirstIfNotEmpty(TokenType.IF, TokenType.PROPERTY_NAME);
        assertTokensHasOnlyOne(TokenType.PROPERTY_NAME, tokens);
    }

    @Test
    public void acceptText() {
        Tokens tokens = tokensWithString("foo*bar");
        tokens.acceptFirstIfNotEmpty(TokenType.FOR, TokenType.PROPERTY_NAME, TokenType.TEXT);
        assertTokensHasOnlyOne(TokenType.TEXT, tokens);
    }

    @Test
    public void acceptPropertyNameEvenIfKeyword() {
        Tokens tokens = tokensWithString(Keyword.ELSE.getStringRepresentation());
        tokens.acceptFirstIfNotEmpty(TokenType.PROPERTY_NAME, TokenType.ELSE);
        assertTokensHasOnlyOne(TokenType.PROPERTY_NAME, tokens);
    }

    private void assertTokensHasOnlyOne(final TokenType type, final Tokens tokens) {
        List<Token> acceptedTokens = tokens.all();
        Assert.assertEquals(1, acceptedTokens.size());
        Assert.assertEquals(type, acceptedTokens.get(0).getType());
    }

    private Tokens tokensWithString(final String stringRepresentation) {
        Tokens tokens = new Tokens();
        stringRepresentation.chars().forEach(c -> tokens.append((char) c));
        return tokens;
    }
}
