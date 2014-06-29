package org.watertemplate;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class StaticTemplatesCacheTest {

    private static final Locale LOCALE = TemplateRenderer.DEFAULT_LOCALE;

    @Test
    public void uncachedTemplate() {
        Assert.assertFalse(StaticTemplatesCache.contains(Fixture.StaticTemplate.class, LOCALE));
        Assert.assertNull(StaticTemplatesCache.get(Fixture.StaticTemplate.class, LOCALE));
    }

    @Test
    public void cachedTemplate() {
        Template testTemplate = new Fixture.StaticTemplate();
        StaticTemplatesCache.cacheIfNecessary(Fixture.StaticTemplate.class, LOCALE, testTemplate.render());

        Assert.assertTrue(StaticTemplatesCache.contains(Fixture.StaticTemplate.class, LOCALE));
        Assert.assertNotNull(StaticTemplatesCache.get(Fixture.StaticTemplate.class, LOCALE));
    }
}
