package org.watertemplate.interpreter.lexer;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class WaterLexerTest {
    @Test
    public void lex() throws Exception {

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("foo", true);
        arguments.put("bar", false);

        WaterLexer lexer = new WaterLexer(arguments);

        String s = "  " +
            "~foo~    " +
            "~bar~    " +
            "~if foo: " +
            "   ~foo~ " +
            "~else:   " +
            "   ~bar~ " +
            "~    " +
            "<span /> ";

        String[] expected = new String[]{"~", "foo", "~", "~", "bar", "~", "~", "if foo", ":", "~", "foo", "~", "~",
            "else", ":", "~", "bar", "~", "~", "<span />"};

        for (int i = 0; i < s.length(); i++) {
            lexer.lex(s.charAt(i));
        }

        lexer.lex('\0');
        Assert.assertArrayEquals(expected, lexer.getTokens());
    }
}
