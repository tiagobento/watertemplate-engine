package org.watertemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class TemplateMap<T> {

    final Map<String, T> map = new HashMap<>();

    final void add(final String key, final T value) {
        this.map.put(key, value);
    }

    public static class SubTemplates extends TemplateMap<TemplateObject.SubTemplateObject> {
        SubTemplates() {
        }

        public final void add(final String key, final Template subTemplate) {
            add(key, new TemplateObject.SubTemplateObject(subTemplate));
        }
    }

    public static final class Arguments extends TemplateMap<TemplateObject> {
        public Arguments() {
        }

        public Arguments(final Arguments arguments) {
            map.putAll(arguments.map);
        }

        public final <T> void addCollection(final String key, final Collection<T> iterable) {
            add(key, new TemplateObject.CollectionObject<>(iterable, (a, b) -> {
            }));
        }

        public final <T> void addCollection(final String key, final Collection<T> iterable, final BiConsumer<T, Arguments> mapper) {
            add(key, new TemplateObject.CollectionObject<>(iterable, mapper));
        }

        public final <T> void addMappedObject(final String key, final T object, final BiConsumer<T, Arguments> mapper) {
            add(key, new TemplateObject.MappedObject<>(object, mapper));
        }

        public final <T> void addLocaleSensitiveObject(final String key, final T object, final BiFunction<T, Locale, String> function) {
            add(key, new TemplateObject.LocaleSensitiveObject<>(object, function));
        }

        public final void add(final String key, final String value) {
            add(key, new TemplateObject.StringObject(value));
        }

        public final void add(final String key, final Boolean value) {
            add(key, new TemplateObject.ConditionObject(value));
        }

        public final TemplateObject get(final String key) {
            return map.get(key);
        }
    }
}
