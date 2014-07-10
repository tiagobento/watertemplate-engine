package org.watertemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class TemplateMap<T> {
    final Map<String, T> map = new HashMap<>();

    public final void add(final String key, final T value) {
        this.map.put(key, value);
    }

    //

    private static abstract class Mappable<T> {
        private final BiConsumer<T, Arguments> mapper;

        Mappable(final BiConsumer<T, Arguments> mapper) {
            this.mapper = mapper;
        }

        Map<String, Object> map(final T object) {
            Arguments arguments = new Arguments();
            mapper.accept(object, arguments);
            return arguments.map;
        }
    }

    static class TemplateObject<T> extends Mappable<T> {
        private final T object;

        TemplateObject(final T object, final BiConsumer<T, Arguments> mapper) {
            super(mapper);
            this.object = object;
        }

        Map<String, Object> map() {
            return map(object);
        }

        @Override
        public String toString() {
            return object.toString();
        }
    }

    static class TemplateCollection<T> extends Mappable<T> {
        private final Iterable<T> iterable;

        TemplateCollection(final Iterable<T> iterable, final BiConsumer<T, Arguments> mapper) {
            super(mapper);
            this.iterable = iterable;
        }

        @Override
        public String toString() {
            return iterable.toString();
        }
    }

    //

    public static class Arguments extends TemplateMap<Object> {
        public final <T> void addCollection(final String key, final Iterable<T> iterable, final BiConsumer<T, Arguments> mapper) {
            add(key, new TemplateCollection<>(iterable, mapper));
        }

        public final <T> void addMappedObject(final String key, final T object, final BiConsumer<T, Arguments> mapper) {
            add(key, new TemplateObject<>(object, mapper));
        }

        public final String get(final String key) {
            return "" + map.get(key);
        }
    }

    public static class SubTemplates extends TemplateMap<Template> {
    }
}
