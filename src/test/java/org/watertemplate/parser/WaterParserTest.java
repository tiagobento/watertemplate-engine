package org.watertemplate.parser;

import org.junit.Assert;
import org.junit.Test;
import org.watertemplate.TemplateArguments;

public class WaterParserTest {

    @Test
    public void instantiation() {
        Parser parser = new WaterParser("templates/foo.html", new TemplateArguments());

        Assert.assertNotNull(parser);
    }
}
