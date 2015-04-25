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
        return parse(lex(templateFile())).string(arguments, locale);
    }

    private File templateFile() {
        final String templateFileURI = new File("").getAbsolutePath() + File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "templates" + File.separator +
                defaultLocale + File.separator +
                templateFilePath;

        final File file = new File(templateFileURI);

        if (!file.exists()) {
            throw new TemplateFileNotFoundException(templateFilePath);
        }

        return file;
    }
}
