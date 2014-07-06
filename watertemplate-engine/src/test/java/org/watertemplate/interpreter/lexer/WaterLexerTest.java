package org.watertemplate.interpreter.lexer;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class WaterLexerTest {
    @Test
    public void lex() throws Exception {

        final String exampleTemplateFile = "" +
            "~foo~    " +
            "~bar~    " +
            "~if foo: " +
            "   ~foo~ " +
            "~else:   " +
            "   ~bar~ " +
            "~        " +
            "<span /> ";

        String[] expected = new String[]{"~", "foo", "~", "~", "bar", "~", "~", "if foo", ":", "~", "foo", "~", "~",
            "else", ":", "~", "bar", "~", "~", "<span />"};

        final WaterLexer lexer = new WaterLexer();
        exampleTemplateFile.chars().forEach((c) -> lexer.lex((char) c));
        lexer.lex('\0');

        final List<String> tokens = lexer.getTokens();
        Assert.assertArrayEquals(expected, tokens.toArray(new String[tokens.size()]));
    }
}
