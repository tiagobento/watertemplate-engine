package org.watertemplate.interpreter.lexer;


import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.watertemplate.interpreter.lexer.TokenType.PROPERTY_NAME;

public class TypeTest {

    @Test
    public void acceptIds() {
        assertTrue(PROPERTY_NAME.accept("a"));
        assertTrue(PROPERTY_NAME.accept("A"));
        assertTrue(PROPERTY_NAME.accept("ab"));
        assertTrue(PROPERTY_NAME.accept("aA"));
        assertTrue(PROPERTY_NAME.accept("aAc"));
        assertTrue(PROPERTY_NAME.accept("aAc_a"));
        assertTrue(PROPERTY_NAME.accept("aAc_agg"));
        assertTrue(PROPERTY_NAME.accept("aA0c_ag2g45"));
        assertFalse(PROPERTY_NAME.accept("9aAc_agg"));
        assertFalse(PROPERTY_NAME.accept("~aAc_agg"));
        assertFalse(PROPERTY_NAME.accept("aAc_a:gg"));
    }
}
