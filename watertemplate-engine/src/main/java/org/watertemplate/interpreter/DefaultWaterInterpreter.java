package org.watertemplate.interpreter;

import org.watertemplate.TemplateMap;
import org.watertemplate.interpreter.exception.TemplateFileNotFoundException;
import org.watertemplate.interpreter.lexer.Lexer;
import org.watertemplate.interpreter.lexer.Token;
import org.watertemplate.interpreter.parser.AbstractSyntaxTree;
import org.watertemplate.interpreter.parser.Parser;
import org.watertemplate.interpreter.reader.Reader;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;

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

    private File templateFileWith(final Locale locale) {
        final String templateFileURI = "templates" + File.separator + locale + File.separator + templateFilePath;
        final URL url = getClass().getClassLoader().getResource(templateFileURI);

        if (url != null) {
            return new File(url.getFile());
        }

        if (!locale.equals(defaultLocale)) {
            return templateFileWith(defaultLocale);
        }

        throw new TemplateFileNotFoundException(templateFilePath);
    }

    private String cacheKey(final Locale locale) {
        return templateFilePath + locale;
    }
}
