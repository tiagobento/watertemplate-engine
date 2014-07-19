package org.watertemplate.interpreter.lexer;


import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.watertemplate.interpreter.lexer.TokenType.PROPERTY_KEY;

public class TypeTest {

    @Test
    public void acceptIds() {
        assertTrue(PROPERTY_KEY.accept("a"));
        assertTrue(PROPERTY_KEY.accept("A"));
        assertTrue(PROPERTY_KEY.accept("ab"));
        assertTrue(PROPERTY_KEY.accept("aA"));
        assertTrue(PROPERTY_KEY.accept("aAc"));
        assertTrue(PROPERTY_KEY.accept("aAc_a"));
        assertTrue(PROPERTY_KEY.accept("aAc_agg"));
        assertTrue(PROPERTY_KEY.accept("aA0c_ag2g45"));
        assertFalse(PROPERTY_KEY.accept("9aAc_agg"));
        assertFalse(PROPERTY_KEY.accept("~aAc_agg"));
        assertFalse(PROPERTY_KEY.accept("aAc_a:gg"));
    }
}
