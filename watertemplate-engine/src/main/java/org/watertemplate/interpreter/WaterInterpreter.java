package org.watertemplate.interpreter;

import org.watertemplate.TemplateMap;
import org.watertemplate.interpreter.exception.TemplateFileNotFoundException;
import org.watertemplate.interpreter.parser.AbstractSyntaxTree;
import org.watertemplate.interpreter.parser.Lexer;
import org.watertemplate.interpreter.parser.Parser;
import org.watertemplate.interpreter.parser.Token;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;

public abstract class WaterInterpreter {

    protected final String templateFilePath;
    protected final Locale defaultLocale;

    WaterInterpreter(final String templateFilePath, final Locale defaultLocale) {
        this.templateFilePath = templateFilePath;
        this.defaultLocale = defaultLocale;
    }

    public abstract String string(final TemplateMap.Arguments arguments, final Locale locale);

    AbstractSyntaxTree parse(final List<Token> tokens) {
        return new Parser().parse(tokens);
    }

    List<Token> lex(final InputStream stream) {
        return new Lexer().lex(stream);
    }

    InputStream templateFileWith(final Locale locale) {
        final String templateFileURI = "templates/" + locale + "/" + templateFilePath;
        InputStream stream = getClass().getClassLoader().getResourceAsStream(templateFileURI);

        if (stream == null && !locale.equals(defaultLocale)) {
            stream = templateFileWith(defaultLocale);
        }

        if (stream == null) {
            throw new TemplateFileNotFoundException(templateFilePath);
        }

        return stream;
    }

    //

    public static WaterInterpreter instantiate(final String filePath, final Locale defaultLocale) {
        return interpreter.apply(filePath, defaultLocale);
    }

    private static WaterInterpreter newDefaultInterpreter(final String filePath, final Locale defaultLocale) {
        return new DefaultWaterInterpreter(filePath, defaultLocale);
    }

    private static WaterInterpreter newDeveloperInterpreter(final String filePath, final Locale defaultLocale) {
        return new DeveloperWaterInterpreter(filePath, defaultLocale);
    }

    private static final BiFunction<String, Locale, WaterInterpreter> interpreter =
            System.getProperty("dev-mode") != null ? WaterInterpreter::newDeveloperInterpreter : WaterInterpreter::newDefaultInterpreter;
}
