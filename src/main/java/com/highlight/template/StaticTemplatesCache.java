package com.highlight.template;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

final class StaticTemplatesCache {

    private static final Map<String, String> CACHED_TEMPLATES = new HashMap<>();

    private StaticTemplatesCache() {
    }

    static void cacheIfNecessary(final Class<? extends Template> clazz, final Locale locale, final String fullyRenderedSelf) {
        if (StaticTemplatesCache.shouldHave(clazz)) {
            CACHED_TEMPLATES.put(toKey(clazz, locale), fullyRenderedSelf);
        }
    }

    static String get(final Class<? extends Template> clazz, final Locale locale) {
        return CACHED_TEMPLATES.get(toKey(clazz, locale));
    }

    static boolean contains(final Class<? extends Template> clazz, final Locale locale) {
        return CACHED_TEMPLATES.containsKey(toKey(clazz, locale));
    }

    //

    private static boolean shouldHave(final Class<? extends Template> clazz) {
        return clazz.getAnnotation(StaticTemplate.class) != null;
    }

    private static String toKey(final Class<? extends Template> clazz, final Locale locale) {
        return clazz.getCanonicalName() + "__" + locale;
    }
}
