package org.watertemplate.commands;

import org.junit.Assert;
import org.junit.Test;
import org.watertemplate.Template;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandTemplatesTest {

    @Test
    public void templateWithFor() {
        final Template templateWithCollection = new CommandTemplatesFixture.TemplateWithFor(1, 2, 3, 4);
        final Template templateWithEmptyCollection = new CommandTemplatesFixture.TemplateWithFor();

        Assert.assertEquals("\n1\n\n2\n\n3\n\n4\n", templateWithCollection.render());
        Assert.assertEquals("\nCollection is empty.\n", templateWithEmptyCollection.render());
    }

    @Test
    public void templateWithIf() {
        final Template templateTrue = new CommandTemplatesFixture.TemplateWithIf(true);
        final Template templateFalse = new CommandTemplatesFixture.TemplateWithIf(false);

        Assert.assertEquals(" true ", templateTrue.render());
        Assert.assertEquals(" false ", templateFalse.render());
    }

    @Test
    public void templateWithRandomWavesAndColons() {
        final Template templateTrue = new CommandTemplatesFixture.TemplateWithRandomWavesAndColons(true);
        final Template templateFalse = new CommandTemplatesFixture.TemplateWithRandomWavesAndColons(false);

        Assert.assertEquals("<link href=\"http://link.water.test.com\" />\n\n~~~~~\n\n\n~\n\n\n:::::",
                templateTrue.render());

        Assert.assertEquals("<link href=\"http://link.water.test.com\" />\n\n~~~~~\n\n\n:\n\n\n:::::",
                templateFalse.render());
    }

    @Test
    public void templateWithNestedCommands() {
        final Template template1 = new CommandTemplatesFixture.TemplateWithNestedCommands(true, true, new ArrayList<>());
        Assert.assertEquals("true-true", template1.render().trim());

        final Template template2 = new CommandTemplatesFixture.TemplateWithNestedCommands(true, false, new ArrayList<>());
        Assert.assertEquals("true-false", template2.render().trim());

        final Template template3 = new CommandTemplatesFixture.TemplateWithNestedCommands(false, true, Arrays.asList("a", "b", "c"));
        Assert.assertEquals("a\n\nb\n\nc", template3.render().trim());

        final Template template4 = new CommandTemplatesFixture.TemplateWithNestedCommands(false, true, new ArrayList<>());
        Assert.assertEquals("No xs.", template4.render().trim());
    }

    @Test
    public void templateWithOnlyText() {
        final Template template = new CommandTemplatesFixture.TemplateWithOnlyText();
        Assert.assertEquals("!text", template.render());
    }
}
