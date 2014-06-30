package org.watertemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class TemplateMap<T> {
    final Map<String, T> map = new HashMap<>();

    public final void add(final String key, final T value) {
        this.map.put(key, value);
    }

    static class Arguments extends TemplateMap<Object> {
        final <T> void add(final String key, final Iterable<T> iterable, final BiConsumer<T, Arguments> mapper) {
            add(key, new TemplateCollection<T>(iterable, mapper));
        }
    }

    public static class SubTemplates extends TemplateMap<Template> {
    }

    static class TemplateCollection<T> {
        private final Iterable<T> iterable;
        private final BiConsumer<T, Arguments> mapper;

        TemplateCollection(final Iterable<T> iterable, final BiConsumer<T, Arguments> mapper) {
            this.iterable = iterable;
            this.mapper = mapper;
        }

        Map<String, Object> map(T object) {
            Arguments arguments = new Arguments();
            mapper.accept(object, arguments);
            return arguments.map;
        }
    }
}
