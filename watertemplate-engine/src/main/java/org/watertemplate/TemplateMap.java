package org.watertemplate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

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

    public static final class LocaleSensitiveObject<T> {
        private final BiFunction<T, Locale, Object> consumer;
        private final T object;

        protected LocaleSensitiveObject(final T object, final BiFunction<T, Locale, Object> consumer) {
            this.consumer = consumer;
            this.object = object;
        }

        public Object apply(final Locale locale) {
            return consumer.apply(object, locale);
        }
    }

    public static final class MappedObject<T> extends Mappable<T> {
        private final T object;

        MappedObject(final T object, final BiConsumer<T, Arguments> mapper) {
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

    public static final class CollectionObject<T> extends Mappable<T> implements Iterable {
        private final Iterable<T> iterable;

        public CollectionObject(final Iterable<T> iterable, final BiConsumer<T, Arguments> mapper) {
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
            add(key, new CollectionObject<>(iterable, (a, b) -> {}));
        }

        public final <T> void addCollection(final String key, final Iterable<T> iterable, final BiConsumer<T, Arguments> mapper) {
            add(key, new CollectionObject<>(iterable, mapper));
        }

        public final <T> void addMappedObject(final String key, final T object, final BiConsumer<T, Arguments> mapper) {
            add(key, new MappedObject<>(object, mapper));
        }

        public <T> void addLocaleSensitiveObject(final String key, final T object, final BiFunction<T, Locale, Object> function) {
            add(key, new LocaleSensitiveObject<>(object, function));
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
