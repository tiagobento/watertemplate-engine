package org.watertemplate.interpreter.reader;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;

public class TemplateReaderTest {
    @Test
    public void testReadExecuting() throws Exception {
        URL url = getClass().getResource("readerTest.html");
        Assert.assertNotNull(url);

        TemplateReader reader = new TemplateReader(new File(url.getFile()));

        StringBuilder stringBuilder = new StringBuilder();
        reader.readExecuting(stringBuilder::append);

        Assert.assertEquals("readerTest.html content\0", stringBuilder.toString());
    }
}
