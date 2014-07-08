package org.watertemplate.interpreter.lexer;


import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.watertemplate.interpreter.lexer.TokenClass.ID;
import static org.watertemplate.interpreter.lexer.TokenClass.KEYWORD;

public class TokenClassTest {

    @Test
    public void acceptIds() {
        assertTrue(ID.accept("a"));
        assertTrue(ID.accept("A"));
        assertTrue(ID.accept("ab"));
        assertTrue(ID.accept("aA"));
        assertTrue(ID.accept("aAc"));
        assertTrue(ID.accept("aAc_a"));
        assertTrue(ID.accept("aAc_agg"));
        assertTrue(ID.accept("aA0c_ag2g45"));
        assertFalse(ID.accept("9aAc_agg"));
        assertFalse(ID.accept("~aAc_agg"));
        assertFalse(ID.accept("aAc_a:gg"));
    }

    @Test
    public void acceptKeywords() {
        TokenClass.KEYWORDS.stream().forEach((keyword) -> {
            assertTrue(KEYWORD.accept(keyword));
        });

        assertFalse(KEYWORD.accept("foo"));
        assertFalse(KEYWORD.accept("bar"));
    }
}
