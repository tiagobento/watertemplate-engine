package org.watertemplate;

import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;

public abstract class Template {

    /* Please use me */
    final TemplateMap.Arguments arguments = new TemplateMap.Arguments();

    /* Please override me */
    protected Template getMasterTemplate() {
        return null;
    }

    /* Implement me (you have no choice) */
    protected abstract String getFilePath();

    /* Please override me */
    protected void addSubTemplates(final TemplateMap.SubTemplates map) {
    }

    final String render() {
        return render(TemplateRenderer.DEFAULT_LOCALE);
    }

    final String render(final Locale locale) {
        try {
            return new TemplateRenderer(this, locale).render();
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }

    final Map<String, Template> getSubTemplates() {
        TemplateMap.SubTemplates subTemplates = new TemplateMap.SubTemplates();
        addSubTemplates(subTemplates);
        return subTemplates.map;
    }

    protected final void add(final String key, final Object value) {
        this.arguments.add(key, value);
    }

    protected final <T> void add(final String key, final Iterable<T> iterable, final BiConsumer<T, TemplateMap.Arguments> mapper) {
        this.arguments.add(key, iterable, mapper);
    }
}


