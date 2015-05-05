package org.watertemplate;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static org.watertemplate.TemplateObject.SubTemplate;

public abstract class TemplateMap<T> {

    final Map<String, T> map = new HashMap<>();

    final void add(final String key, final T value) {
        this.map.put(key, value);
    }

    public static class SubTemplates extends TemplateMap<SubTemplate> {
        List<SubTemplate> list = new ArrayList<>();

        SubTemplates() {
        }

        public final void add(final String key, final Template subTemplate) {
            SubTemplate templateObject = new SubTemplate(subTemplate);
            add(key, templateObject);
            list.add(templateObject);
        }
    }

    public static final class Arguments extends TemplateMap<TemplateObject> {
        public Arguments() {
        }

        public Arguments(final Arguments arguments) {
            map.putAll(arguments.map);
        }

        public final <T> void addCollection(final String key, final Collection<T> iterable) {
            add(key, new TemplateObject.Collection<T>(iterable, (a, b) -> {
            }));
        }

        public final <T> void addCollection(final String key, final Collection<T> iterable, final BiConsumer<T, Arguments> mapper) {
            add(key, new TemplateObject.Collection<T>(iterable, mapper));
        }

        public final <T> void addMappedObject(final String key, final T object, final BiConsumer<T, Arguments> mapper) {
            add(key, new TemplateObject.Mapped<>(object, mapper));
        }

        public final <T> void addLocaleSensitiveObject(final String key, final T object, final BiFunction<T, Locale, String> function) {
            add(key, new TemplateObject.LocaleSensitive<>(object, function));
        }

        public final void add(final String key, final String value) {
            add(key, new TemplateObject.Value(value));
        }

        public final void add(final String key, final Boolean value) {
            add(key, new TemplateObject.Condition(value));
        }

        public final TemplateObject get(final String key) {
            return map.get(key);
        }

        final void addTemplateWhichWontRenderItsMasterTemplate(final String key, final Template subTemplate) {
            add(key, new SubTemplate.WithoutMaster(subTemplate));
        }
    }
}
