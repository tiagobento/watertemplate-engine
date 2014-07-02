package org.watertemplate.parser;

import org.watertemplate.TemplateException;
import org.watertemplate.parser.reader.TemplateReader;

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
        final WaterAnalyzer analyzer = new WaterAnalyzer();
        final TemplateReader reader = new TemplateReader(getTemplateFile(locale));
        reader.readExecuting(analyzer::analyze);
        return analyzer.getResultString();
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

    private class WaterAnalyzer {
        private final StringBuilder resultStringBuilder;

        private WaterAnalyzer() {
            resultStringBuilder = new StringBuilder();
        }

        public void analyze(final Character character) {
            resultStringBuilder.append(character);

        }

        public String getResultString() {
            return resultStringBuilder.toString();
        }
    }
}
