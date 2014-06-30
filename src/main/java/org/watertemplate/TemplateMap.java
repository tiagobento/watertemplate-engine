package org.watertemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

class TemplateMap {
    final Map<String, Object> map = new HashMap<>();

    final void add(final String key, final Object value) {
        this.map.put(key, value);
    }

    final <T> void add(final String key, final Iterable<T> iterable, final BiConsumer<T, TemplateMap> mapper) {
        add(key, new TemplateCollection<T>(iterable, mapper));
    }

    static class TemplateCollection<T> {
        private final Iterable<T> iterable;
        private final BiConsumer<T, TemplateMap> mapper;

        TemplateCollection(final Iterable<T> iterable, final BiConsumer<T, TemplateMap> mapper) {
            this.iterable = iterable;
            this.mapper = mapper;
        }

        TemplateMap map(T object) {
            TemplateMap map = new TemplateMap();
            mapper.accept(object, map);
            return map;
        }
    }
}
