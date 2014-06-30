package org.watertemplate;

import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;

public abstract class Template {

    /* Please use me */
    final TemplateMap.Arguments arguments = new TemplateMap.Arguments();

    /* Please override me */
    protected void addSubTemplates(final TemplateMap.SubTemplates map) {}

    /* Please override me */
    protected Template getMasterTemplate() {
        return null;
    }

    /* Implement me (you have no choice) */
    protected abstract String getTemplateFilePath();

    //

    final String render() {
        return render(TemplateRenderer.DEFAULT_LOCALE);
    }

    final String render(final Locale locale) {
        try {
            return fromCacheOrNewRendering(locale);
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }

    private String fromCacheOrNewRendering(final Locale locale) {
        if (StaticTemplatesCache.contains(getClass(), locale)) {
            return StaticTemplatesCache.get(getClass(), locale);
        }

        final String rendered = new TemplateRenderer(this, locale).render();
        StaticTemplatesCache.cacheIfNecessary(getClass(), locale, rendered);
        return rendered;
    }

    protected final Map<String, Template> getSubTemplates() {
        TemplateMap.SubTemplates map = new TemplateMap.SubTemplates();
        addSubTemplates(map);
        return map.map;
    }

    protected final void add(final String key, final Object value) {
        this.arguments.add(key, value);
    }

    protected final <T> void  add(final String key, final Iterable<T> iterable, final BiConsumer<T, TemplateMap.Arguments> mapper) {
        this.arguments.add(key, iterable, mapper);
    }
}


