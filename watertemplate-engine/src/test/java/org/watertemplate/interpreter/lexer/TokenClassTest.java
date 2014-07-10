package org.watertemplate.interpreter.lexer;


import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.watertemplate.interpreter.lexer.TokenClass.IDENTIFIER;
import static org.watertemplate.interpreter.lexer.TokenClass.KEYWORD;

public class TokenClassTest {

    @Test
    public void acceptIds() {
        assertTrue(IDENTIFIER.accept("a"));
        assertTrue(IDENTIFIER.accept("A"));
        assertTrue(IDENTIFIER.accept("ab"));
        assertTrue(IDENTIFIER.accept("aA"));
        assertTrue(IDENTIFIER.accept("aAc"));
        assertTrue(IDENTIFIER.accept("aAc_a"));
        assertTrue(IDENTIFIER.accept("aAc_agg"));
        assertTrue(IDENTIFIER.accept("aA0c_ag2g45"));
        assertFalse(IDENTIFIER.accept("9aAc_agg"));
        assertFalse(IDENTIFIER.accept("~aAc_agg"));
        assertFalse(IDENTIFIER.accept("aAc_a:gg"));
    }

    @Test
    public void acceptKeywords() {
        TokenClass.KEYWORDS.stream().forEach((keyword) -> assertTrue(KEYWORD.accept(keyword)));

        assertFalse(KEYWORD.accept("foo"));
        assertFalse(KEYWORD.accept("bar"));
    }
}
