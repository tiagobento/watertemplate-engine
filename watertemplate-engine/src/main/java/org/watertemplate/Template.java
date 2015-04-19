package org.watertemplate;

import java.util.Collection;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public abstract class Template {

    private static final Locale DEFAULT_LOCALE = Locale.US;

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

    /* Override me if you want */
    protected Locale getDefaultLocale() {
        return Template.DEFAULT_LOCALE;
    }

    public final String render() {
        return render(getDefaultLocale());
    }

    public final String render(final Locale locale) {
        return new TemplateRenderer(this, locale).render();
    }

    protected final void add(final String key, final String value) {
        this.arguments.add(key, value);
    }

    protected final void add(final String key, final Boolean someCondition) {
        this.arguments.add(key, someCondition);
    }

    protected final <T> void addMappedObject(final String key, final T object, final Consumer<TemplateMap.Arguments> mapper) {
        this.addMappedObject(key, object, (a, b) -> mapper.accept(b));
    }

    protected final <T> void addMappedObject(final String key, final T object, final BiConsumer<T, TemplateMap.Arguments> mapper) {
        this.arguments.addMappedObject(key, object, mapper);
    }

    protected final <T> void addCollection(final String key, final Collection<T> iterable, final BiConsumer<T, TemplateMap.Arguments> mapper) {
        this.arguments.addCollection(key, iterable, mapper);
    }

    protected final <T> void addCollection(final String key, final Collection<T> iterable) {
        this.arguments.addCollection(key, iterable);
    }

    protected final <T> void addLocaleSensitiveObject(final String key, final T object, final BiFunction<T, Locale, String> function) {
        this.arguments.addLocaleSensitiveObject(key, object, function);
    }
}


