package org.watertemplate.interpreter;

import org.watertemplate.TemplateMap;
import org.watertemplate.interpreter.parser.AbstractSyntaxTree;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class DefaultWaterInterpreter extends WaterInterpreter {

    private final static ConcurrentMap<String, AbstractSyntaxTree> cache = new ConcurrentHashMap<>();

    public DefaultWaterInterpreter(final String templateFilePath, final Locale defaultLocale) {
        super(templateFilePath, defaultLocale);
    }

    @Override
    public String string(final TemplateMap.Arguments arguments, final Locale locale) {
        return cache.computeIfAbsent(cacheKey(locale), (key) -> {
            return parse(lex(templateFileWith(locale)));
        }).string(arguments, locale);
    }

    private String cacheKey(final Locale locale) {
        return templateFilePath + locale;
    }
}
