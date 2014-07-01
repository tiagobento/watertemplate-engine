package org.watertemplate.parser;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class WaterParserTest {

    @Test
    public void parse() {
        Parser parser = new WaterParser("beta/v1.html", new HashMap<>());
        Assert.assertNotNull(parser.parse(Parser.DEFAULT_LOCALE));
    }
}
