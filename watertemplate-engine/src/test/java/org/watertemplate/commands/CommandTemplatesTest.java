package org.watertemplate.commands;

import org.junit.Assert;
import org.junit.Test;
import org.watertemplate.Template;

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
}
