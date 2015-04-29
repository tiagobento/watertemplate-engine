package org.watertemplate.interpreter.parser;

import org.junit.Assert;
import org.junit.Test;

import static org.watertemplate.interpreter.parser.TokenFixture.*;

public class TerminalTest {

    @Test
    public void buildParseTree() {
        TokenStream tokenStream = new TokenStream(
                If(),
                PropertyKey("id"),
                PropertyKey("x"),
                EndOfBlock());

        Assert.assertNotNull(Terminal.IF.buildAbstractSyntaxTree(tokenStream));
        Assert.assertNotNull(Terminal.PROPERTY_KEY.buildAbstractSyntaxTree(tokenStream));
        Assert.assertNotNull(Terminal.PROPERTY_KEY.buildAbstractSyntaxTree(tokenStream));
        Assert.assertNotNull(Terminal.END_OF_BLOCK.buildAbstractSyntaxTree(tokenStream));
    }

}
