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

public class WaterInterpreter implements Interpreter {

    private final static Map<String, AbstractSyntaxTree> cache = new HashMap<>();

    private final String templateFilePath;
    private final TemplateMap.Arguments arguments;
    private final Locale defaultLocale;

    public WaterInterpreter(final String templateFilePath, final TemplateMap.Arguments arguments, final Locale defaultLocale) {
        this.templateFilePath = templateFilePath;
        this.arguments = arguments;
        this.defaultLocale = defaultLocale;
    }

    @Override
    public String interpret(final Locale locale) {
        final String cacheKey = cacheKey(locale);

        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey).evaluate(arguments, locale);
        }

        File templateFile = findTemplateFileWith(locale);
        List<Token> tokens = lex(templateFile);
        AbstractSyntaxTree abs = parse(tokens);

        cache.put(cacheKey, abs);
        return abs.evaluate(arguments, locale);
    }

    private File findTemplateFileWith(final Locale locale) {
        final String templateFileURI = "templates" + File.separator + locale + File.separator + templateFilePath;
        final URL url = getClass().getClassLoader().getResource(templateFileURI);

        if (url != null) {
            return new File(url.getFile());
        }

        if (!locale.equals(defaultLocale)) {
            return findTemplateFileWith(defaultLocale);
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
