package org.watertemplate.interpreter.parser;

import org.junit.Assert;
import org.junit.Test;

import static org.watertemplate.interpreter.lexer.TokenFixture.*;

public class TerminalTest {
    @Test
    public void buildParseTree() {
        TokenStream tokenStream = new TokenStream(
            new If(),
            new PropertyName("id"),
            new PropertyName("x"),
            new End());

        Assert.assertNotNull(Terminal.IF.buildParseTreeFor(tokenStream));
        Assert.assertNotNull(Terminal.PROPERTY_NAME.buildParseTreeFor(tokenStream));
        Assert.assertNotNull(Terminal.PROPERTY_NAME.buildParseTreeFor(tokenStream));
        Assert.assertNotNull(Terminal.END.buildParseTreeFor(tokenStream));
    }

}
