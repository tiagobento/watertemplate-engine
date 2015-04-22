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

public class WaterInterpreter {

    private final static ConcurrentMap<String, AbstractSyntaxTree> cache = new ConcurrentHashMap<>();

    private final String templateFilePath;
    private final Locale defaultLocale;

    public WaterInterpreter(final String templateFilePath, final Locale defaultLocale) {
        this.templateFilePath = templateFilePath;
        this.defaultLocale = defaultLocale;
    }

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

    private List<Token> lex(final File templateFile) {
        final Lexer lexer = new Lexer();

        final Reader reader = new Reader(templateFile);
        reader.readExecuting(lexer::accept);

        final List<Token> tokens = lexer.getTokens();
        tokens.add(Token.END_OF_INPUT);
        return tokens;
    }

    private AbstractSyntaxTree parse(final List<Token> tokens) {
        return new Parser(tokens).buildAbstractSyntaxTree();
    }

    private String cacheKey(final Locale locale) {
        return templateFilePath + locale;
    }
}
