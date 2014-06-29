package com.highlight.template;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class Template {

    /* Please use me */
    final Map<String, Object> args = new HashMap<>();

    /* Please override me */
    protected Map<String, Template> getSubTemplates() {
        return new HashMap<>();
    }

    /* Please override me */
    protected Template getMasterTemplate() {
        return null;
    }

    /* Implement me (you have no choice) */
    protected abstract String getTemplateFileURI();

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

    private String fromCacheOrNewRendering(final Locale locale) throws IOException {
        if (StaticTemplatesCache.contains(getClass(), locale)) {
            return StaticTemplatesCache.get(getClass(), locale);
        } else {
            final String rendered = new TemplateRenderer(this, locale).render();
            StaticTemplatesCache.cacheIfNecessary(getClass(), locale, rendered);
            return rendered;
        }
    }

    protected final void add(final String key, final Object value) {
        this.args.put(key, value);
    }
}


