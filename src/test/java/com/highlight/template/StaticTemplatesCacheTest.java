package com.highlight.template;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class StaticTemplatesCacheTest {

    private static final Locale LOCALE = TemplateRenderer.DEFAULT_LOCALE;

    @StaticTemplate
    public static class StaticTestTemplate extends Fixture.SubTemplate {
    }

    @Test
    public void testUncachedTemplate() {
        Assert.assertFalse(StaticTemplatesCache.contains(StaticTestTemplate.class, LOCALE));
        Assert.assertNull(StaticTemplatesCache.get(StaticTestTemplate.class, LOCALE));
    }

    @Test
    public void testCachedTemplate() {
        Template testTemplate = new StaticTestTemplate();
        StaticTemplatesCache.cacheIfNecessary(StaticTestTemplate.class, LOCALE, testTemplate.render());

        Assert.assertTrue(StaticTemplatesCache.contains(StaticTestTemplate.class, LOCALE));
        Assert.assertNotNull(StaticTemplatesCache.get(StaticTestTemplate.class, LOCALE));
    }
}
