package org.watertemplate;

import java.util.HashMap;
import java.util.Map;

class TemplateMap {
    final Map<String, Object> map = new HashMap<>();

    final void add(final String key, final Object value) {
        this.map.put(key, value);
    }

    final <T> void add(final String key, final Iterable<T> iterable, final CollectionMapper<T> mapper) {
        add(key, new Collection<T>(iterable, mapper));
    }

    //

    abstract static class CollectionMapper<T> {
        public abstract void map(final T object, final TemplateMap map);

        final TemplateMap map(final T object) {
            TemplateMap map = new TemplateMap();
            map(object, map);
            return map;
        }
    }

    static class Collection<T> {
        private final Iterable<T> iterable;
        private final CollectionMapper<T> mapper;

        Collection(final Iterable<T> iterable, final CollectionMapper<T> mapper) {
            this.iterable = iterable;
            this.mapper = mapper;
        }
    }
}
