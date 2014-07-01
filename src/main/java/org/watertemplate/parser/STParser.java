package org.watertemplate.parser;

import org.apache.commons.io.FileUtils;
import org.stringtemplate.v4.ST;
import org.watertemplate.TemplateException;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Locale;
import java.util.Map;


/* Temporary parser */
public final class STParser implements Parser {

    private final Map<String, Object> arguments;
    private final String templateFilePath;

    public STParser(final String templateFilePath, final Map<String, Object> arguments) {
        this.templateFilePath = templateFilePath;
        this.arguments = arguments;
    }

    @Override
    public String parse(final Locale locale) {
        try {
            File templateFile = getTemplateFile(locale);
            ST st = new ST(FileUtils.readFileToString(templateFile), DELIMITER, DELIMITER);
            this.addAllArguments(st, arguments);
            return st.render();
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }

    private void addAllArguments(final ST st, final Map<String, Object> args) {
        for (final Map.Entry<String, Object> argsEntry : args.entrySet())
            st.add(argsEntry.getKey(), argsEntry.getValue());
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
