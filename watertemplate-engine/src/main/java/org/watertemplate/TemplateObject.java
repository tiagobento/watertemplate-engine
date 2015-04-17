package org.watertemplate;

import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public interface TemplateObject<T> {
    T evaluate(final Locale locale);

    public final class LocaleSensitiveObject<T> implements TemplateObject<String> {
        private final BiFunction<T, Locale, String> consumer;
        private final T object;

        protected LocaleSensitiveObject(final T object, final BiFunction<T, Locale, String> consumer) {
            this.consumer = consumer;
            this.object = object;
        }

        @Override
        public String evaluate(final Locale locale) {
            return consumer.apply(object, locale);
        }
    }

    public final class MappedObject<T> extends Mappable<T> implements TemplateObject<T> {
        private final T object;

        MappedObject(final T object, final BiConsumer<T, TemplateMap.Arguments> mapper) {
            super(mapper);
            this.object = object;
        }

        public TemplateMap.Arguments map() {
            return map(object);
        }

        @Override
        public T evaluate(Locale locale) {
            return object;
        }
    }

    public final class CollectionObject<T> extends Mappable<T> implements TemplateObject<CollectionObject<T>> {
        private final Iterable<T> iterable;

        public CollectionObject(final Iterable<T> iterable, final BiConsumer<T, TemplateMap.Arguments> mapper) {
            super(mapper);
            this.iterable = iterable;
        }

        public Boolean isEmpty() {
            return iterable == null || !iterable.iterator().hasNext();
        }

        public Iterable<T> getIterable() {
            return iterable;
        }

        @Override
        public CollectionObject<T> evaluate(Locale locale) {
            return this;
        }
    }

    public class ConditionObject implements TemplateObject<Boolean> {
        private final Boolean value;

        public ConditionObject(final Boolean value) {
            this.value = value;
        }

        @Override
        public Boolean evaluate(Locale locale) {
            return value;
        }
    }

    public class StringObject implements TemplateObject<String> {
        private final String value;

        public StringObject(final String value) {
            this.value = value;
        }

        @Override
        public String evaluate(final Locale locale) {
            return value;
        }
    }

    //

    static abstract class Mappable<T> {
        private final BiConsumer<T, TemplateMap.Arguments> mapper;

        Mappable(final BiConsumer<T, TemplateMap.Arguments> mapper) {
            this.mapper = mapper;
        }

        public TemplateMap.Arguments map(final T object) {
            TemplateMap.Arguments arguments = new TemplateMap.Arguments();
            mapper.accept(object, arguments);
            return arguments;
        }

        public BiConsumer<T, TemplateMap.Arguments> getMapper() {
            return mapper;
        }
    }
}
