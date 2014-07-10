package org.watertemplate.interpreter.lexer;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TokensTest {

    @Test
    public void acceptKeyword() {
        Tokens tokens = tokensWithString(Keyword.ELSE.getStringRepresentation());
        tokens.acceptFirstIfNotEmpty(TokenClass.KEYWORD);
        assertTokensHasOnlyOne(TokenClass.KEYWORD, tokens);
    }

    @Test
    public void acceptIdentifier() {
        Tokens tokens = tokensWithString("foobar");
        tokens.acceptFirstIfNotEmpty(TokenClass.KEYWORD, TokenClass.IDENTIFIER);
        assertTokensHasOnlyOne(TokenClass.IDENTIFIER, tokens);
    }

    @Test
    public void acceptText() {
        Tokens tokens = tokensWithString("foo*bar");
        tokens.acceptFirstIfNotEmpty(TokenClass.KEYWORD, TokenClass.IDENTIFIER, TokenClass.TEXT);
        assertTokensHasOnlyOne(TokenClass.TEXT, tokens);
    }

    @Test
    public void acceptIdentifierEvenIfKeyword() {
        Tokens tokens = tokensWithString(Keyword.ELSE.getStringRepresentation());
        tokens.acceptFirstIfNotEmpty(TokenClass.IDENTIFIER, TokenClass.KEYWORD);
        assertTokensHasOnlyOne(TokenClass.IDENTIFIER, tokens);
    }

    private void assertTokensHasOnlyOne(final TokenClass identifier, final Tokens tokens) {
        List<Token> acceptedTokens = tokens.all();
        Assert.assertEquals(1, acceptedTokens.size());
        Assert.assertEquals(identifier, acceptedTokens.get(0).getTokenClass());
    }

    private Tokens tokensWithString(final String stringRepresentation) {
        Tokens tokens = new Tokens();
        stringRepresentation.chars().forEach(c -> tokens.append((char) c));
        return tokens;
    }
}
