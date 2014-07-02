package org.watertemplate.parser;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class WaterParserTest {

    @Test
    public void parseSimpleArgument() {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("foo", "bar");

        Parser parser = getParserForSingleArgumentTemplate(arguments);
        Assert.assertEquals("bar", parser.parse(Parser.DEFAULT_LOCALE));
    }

    @Test
    public void parseSimpleArgumentWithMissingArgument() {
        HashMap<String, Object> arguments = new HashMap<>();

        Parser parser = getParserForSingleArgumentTemplate(arguments);
        Assert.assertEquals("[MISSING: foo]", parser.parse(Parser.DEFAULT_LOCALE));
    }

    private Parser getParserForSingleArgumentTemplate(HashMap<String, Object> arguments) {
        return new WaterParser("beta/simpleArgument.html", arguments);
    }
}
