package org.watertemplate.parser;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class WaterParserTest {

//    @Test
    public void parseSimpleArgument() {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("foo", "bar");

        Parser parser = new WaterParser("beta/simpleArgument.html", arguments);
        Assert.assertEquals("bar", parser.parse(Parser.DEFAULT_LOCALE));
    }
}
