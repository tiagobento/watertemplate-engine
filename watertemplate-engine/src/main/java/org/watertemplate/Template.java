package org.watertemplate;

import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
    protected void addSubTemplates(final TemplateMap.SubTemplates subTemplates) {
    }

    public final String render() {
        return render(TemplateRenderer.DEFAULT_LOCALE);
    }

    public final String render(final Locale locale) {
        return new TemplateRenderer(this, locale).render();
    }

    final Map<String, Template> getSubTemplates() {
        TemplateMap.SubTemplates subTemplates = new TemplateMap.SubTemplates();
        addSubTemplates(subTemplates);
        return subTemplates.map;
    }

    protected final void add(final String key, final Object value) {
        this.arguments.add(key, value);
    }

    protected final <T> void addMappedObject(final String key, final T object, final Consumer<TemplateMap.Arguments> mapper) {
        this.addMappedObject(key, object, (a, b) -> mapper.accept(b));
    }

    protected final <T> void addMappedObject(final String key, final T object, final BiConsumer<T, TemplateMap.Arguments> mapper) {
        this.arguments.addMappedObject(key, object, mapper);
    }

    protected final <T> void addCollection(final String key, final Iterable<T> iterable, final BiConsumer<T, TemplateMap.Arguments> mapper) {
        this.arguments.addCollection(key, iterable, mapper);
    }
}


