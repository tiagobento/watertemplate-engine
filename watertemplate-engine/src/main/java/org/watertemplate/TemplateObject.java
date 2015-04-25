package org.watertemplate;

import org.watertemplate.exception.InvalidTemplateObjectEvaluationException;

import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public interface TemplateObject {
    String string(final Locale locale);

    class LocaleSensitive<T> implements TemplateObject {
        private final BiFunction<T, Locale, String> function;
        private final T object;

        protected LocaleSensitive(final T object, final BiFunction<T, Locale, String> function) {
            this.function = function;
            this.object = object;
        }

        @Override
        public String string(final Locale locale) {
            return function.apply(object, locale);
        }
    }

    public final class Mapped<T> extends Mappable<T> implements TemplateObject {
        private final T object;
        private final TemplateMap.Arguments mappedProperties;

        Mapped(final T object, final BiConsumer<T, TemplateMap.Arguments> mapper) {
            super(mapper);
            this.object = object;
            this.mappedProperties = map(object);
        }

        public TemplateMap.Arguments map() {
            return mappedProperties;
        }

        @Override
        public String string(final Locale locale) {
            if (object instanceof String) {
                return (String) object;
            } else {
                throw new InvalidTemplateObjectEvaluationException(
                        "MappedObjects should not be evaluated. " +
                                "If you're iterating, make sure your collection contains only Strings.");
            }
        }
    }

    public final class Collection<T> extends Mappable<T> implements TemplateObject {
        private final java.util.Collection<T> collection;

        public Collection(final java.util.Collection<T> collection, final BiConsumer<T, TemplateMap.Arguments> mapper) {
            super(mapper);
            this.collection = collection;
        }

        public Boolean isEmpty() {
            return collection == null || !collection.iterator().hasNext();
        }

        public java.util.Collection<T> getCollection() {
            return collection;
        }

        @Override
        public String string(final Locale locale) {
            throw new InvalidTemplateObjectEvaluationException("Collections should not be evaluated");
        }
    }

    public class Condition implements TemplateObject {
        private final Boolean value;

        public Condition(final Boolean value) {
            this.value = value;
        }

        public Boolean isTrue() {
            return value;
        }

        @Override
        public String string(final Locale locale) {
            throw new InvalidTemplateObjectEvaluationException("Booleans should not be evaluated");
        }
    }

    class Value implements TemplateObject {
        private final String value;

        public Value(final String value) {
            this.value = value;
        }

        @Override
        public String string(final Locale locale) {
            return value;
        }
    }

    class SubTemplate implements TemplateObject {
        final Template subTemplate;

        public SubTemplate(final Template subTemplate) {
            this.subTemplate = subTemplate;
        }

        @Override
        public String string(final Locale locale) {
            return subTemplate.render(locale);
        }

        public static class WithoutMaster extends SubTemplate {
            public WithoutMaster(Template subTemplate) {
                super(subTemplate);
            }

            @Override
            public String string(final Locale locale) {
                return subTemplate.renderWithoutMaster(locale);
            }
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
