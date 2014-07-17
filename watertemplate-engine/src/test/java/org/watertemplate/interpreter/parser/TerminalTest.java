package org.watertemplate.interpreter.parser;

import org.junit.Assert;
import org.junit.Test;

import static org.watertemplate.interpreter.lexer.TokenFixture.*;

public class TerminalTest {
    @Test
    public void matches() {
        TokenStream tokenStream = new TokenStream(
            new If(),
            new PropertyName("id"),
            new PropertyName("x"),
            new End());

        Assert.assertTrue(Terminal.IF.matches(tokenStream));
        Assert.assertTrue(Terminal.PROPERTY_NAME.matches(tokenStream));
        Assert.assertTrue(Terminal.PROPERTY_NAME.matches(tokenStream));
        Assert.assertTrue(Terminal.END.matches(tokenStream));
    }

}
