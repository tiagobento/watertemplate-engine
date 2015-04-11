package org.watertemplate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;

public class TemplateMap<T> {
    public final Map<String, T> map = new HashMap<>();

    public final void add(final String key, final T value) {
        this.map.put(key, value);
    }

    //

    private static abstract class Mappable<T> {
        private final BiConsumer<T, Arguments> mapper;

        Mappable(final BiConsumer<T, Arguments> mapper) {
            this.mapper = mapper;
        }

        public Arguments map(final T object) {
            Arguments arguments = new Arguments();
            mapper.accept(object, arguments);
            return arguments;
        }

        public BiConsumer<T, Arguments> getMapper() {
            return mapper;
        }
    }

    public static final class TemplateObject<T> extends Mappable<T> {
        private final T object;

        TemplateObject(final T object, final BiConsumer<T, Arguments> mapper) {
            super(mapper);
            this.object = object;
        }

        public Arguments map() {
            return map(object);
        }

        @Override
        public String toString() {
            return object.toString();
        }
    }

    public static final class TemplateCollection<T> extends Mappable<T> implements Iterable {
        private final Iterable<T> iterable;

        public TemplateCollection(final Iterable<T> iterable, final BiConsumer<T, Arguments> mapper) {
            super(mapper);
            this.iterable = iterable;
        }

        @Override
        public String toString() {
            return iterable.toString();
        }

        @Override
        public Iterator iterator() {
            return iterable == null ? null : iterable.iterator();
        }
    }

    //

    public static final class Arguments extends TemplateMap<Object> {
        public final <T> void addCollection(final String key, final Iterable<T> iterable) {
            add(key, new TemplateCollection<>(iterable, (a, b) -> {}));
        }

        public final <T> void addCollection(final String key, final Iterable<T> iterable, final BiConsumer<T, Arguments> mapper) {
            add(key, new TemplateCollection<>(iterable, mapper));
        }

        public final <T> void addMappedObject(final String key, final T object, final BiConsumer<T, Arguments> mapper) {
            add(key, new TemplateObject<>(object, mapper));
        }

        public final String get(final String key) {
            return "" + map.get(key);
        }

        public final Object getObject(final String key) {
            return map.get(key);
        }

        public void remove(final String key) {
            map.remove(key);
        }
    }

    public static class SubTemplates extends TemplateMap<Template> {
    }
}
