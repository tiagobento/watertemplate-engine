package org.watertemplate;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class TemplateTest {

    @Test
    public void render() {
        Template template = new Fixture.SubTemplate();
        Assert.assertNotNull(template.render());
    }

    @Test
    public void renderStaticTemplate() {
        Template template = new Fixture.StaticTemplate();
        template.render(Locale.CANADA);

        Assert.assertTrue(StaticTemplatesCache.contains(Fixture.StaticTemplate.class, Locale.CANADA));
        Assert.assertNotNull(StaticTemplatesCache.get(Fixture.StaticTemplate.class, Locale.CANADA));
    }
}
