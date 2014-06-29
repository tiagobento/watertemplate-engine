package org.watertemplate.parser;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class WaterParserTest {

    @Test
    public void instantiation() {
        Parser parser = new WaterParser("templates/foo.html", new HashMap<String, Object>());

        Assert.assertNotNull(parser);
    }
}
