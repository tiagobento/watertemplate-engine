package org.watertemplate.parser;

import org.watertemplate.TemplateException;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Locale;
import java.util.Map;

public class WaterParser implements Parser {

    private final Map<String, Object> arguments;
    private final String templateFilePath;

    public WaterParser(final String templateFilePath, final Map<String, Object> arguments) {
        this.templateFilePath = templateFilePath;
        this.arguments = arguments;
    }

    @Override
    public String parse(final Locale locale) {
        final File file = getTemplateFile(locale);
        return null;
    }

    private File getTemplateFile(final Locale locale) {
        final String templateFileURI = "templates" + File.separator + locale + File.separator + templateFilePath;
        final URL url = getClass().getClassLoader().getResource(templateFileURI);

        if (url != null) {
            return new File(url.getFile());
        }

        if (!locale.equals(DEFAULT_LOCALE)) {
            return getTemplateFile(DEFAULT_LOCALE);
        }

        throw new TemplateException(new FileNotFoundException(templateFilePath));
    }

}
