package org.watertemplate.interpreter;

import org.watertemplate.TemplateMap;
import org.watertemplate.interpreter.exception.TemplateFileNotFoundException;

import java.io.File;
import java.util.Locale;

class DeveloperWaterInterpreter extends WaterInterpreter {
    public DeveloperWaterInterpreter(final String templateFilePath, final Locale defaultLocale) {
        super(templateFilePath, defaultLocale);
    }

    @Override
    public String string(final TemplateMap.Arguments arguments, final Locale locale) {
        return parse(lex(templateFileWith(locale))).string(arguments, locale);
    }
}
