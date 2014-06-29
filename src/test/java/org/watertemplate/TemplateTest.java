package org.watertemplate;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class TemplateTest {

    @Test
    public void testRender() {
        Template template = new Fixture.SubTemplate();
        Assert.assertNotNull(template.render());
    }

    @Test
    public void testRenderStatic() {
        Template template = new Fixture.StaticTemplate();
        template.render(Locale.CANADA);

        Assert.assertTrue(StaticTemplatesCache.contains(Fixture.StaticTemplate.class, Locale.CANADA));
        Assert.assertNotNull(StaticTemplatesCache.get(Fixture.StaticTemplate.class, Locale.CANADA));
    }

    @Test
    public void testAdd() {
        Template template = new Fixture.SubTemplate();
        template.add("foo", "bar");

        Assert.assertEquals(1, template.args.size());
        Assert.assertEquals("bar", template.args.get("foo"));
    }
}
