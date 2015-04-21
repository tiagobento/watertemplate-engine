package org.watertemplate;

import org.watertemplate.exception.RenderException;
import org.watertemplate.interpreter.WaterInterpreter;

import java.util.Collection;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

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

    ////

    public final String render() {
        return render(getDefaultLocale());
    }

    public final String render(final Locale locale) {
        return buildString(stream(locale));
    }

    final Stream<Supplier<String>> stream(final Locale locale) {
        try {
            final Template masterTemplate = getMasterTemplate();

            if (masterTemplate == null) {
                return streamWithoutMaster(locale);
            }

            masterTemplate.arguments.addTemplateWhichWontRenderItsMasterTemplate("content", this);
            return masterTemplate.stream(locale);
        } catch (RuntimeException e) {
            throw new RenderException(this, locale, e);
        }
    }

    final Stream<Supplier<String>> streamWithoutMaster(final Locale locale) {
        TemplateMap.SubTemplates subTemplates = new TemplateMap.SubTemplates();
        addSubTemplates(subTemplates);
        subTemplates.map.forEach(arguments::add);

        return new WaterInterpreter(getFilePath(), arguments, getDefaultLocale()).stream(locale);
    }

    public static String buildString(final Stream<Supplier<String>> supplierStream) {
        return supplierStream
                .map(Supplier::get)
                .reduce(String::concat)
                .orElse("");
    }
}


