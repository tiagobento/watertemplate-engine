package org.watertemplate.interpreter;

import org.watertemplate.Configuration;
import org.watertemplate.TemplateMap;
import org.watertemplate.interpreter.exception.TemplateFileNotFoundException;
import org.watertemplate.interpreter.lexer.Lexer;
import org.watertemplate.interpreter.lexer.Token;
import org.watertemplate.interpreter.parser.AbstractSyntaxTree;
import org.watertemplate.interpreter.parser.Parser;
import org.watertemplate.interpreter.reader.Reader;

import java.io.File;
import java.net.URL;
import java.util.*;

public class WaterInterpreter implements Interpreter {

    private final static Map<String, AbstractSyntaxTree> cache = new HashMap<>();

    private final String templateFilePath;
    private final TemplateMap.Arguments arguments;
    private final Configuration configuration;

    public WaterInterpreter(final String templateFilePath, final TemplateMap.Arguments arguments, final Configuration configuration) {
        this.templateFilePath = templateFilePath;
        this.arguments = arguments;
        this.configuration = configuration;
    }

    @Override
    public String interpret(final Locale locale) {
        final String cacheKey = cacheKey(locale);

        if (cache.containsKey(cacheKey)) {
            return (String) cache.get(cacheKey).run(arguments, locale, configuration);
        }

        final File templateFile = findTemplateFileWith(locale);
        final List<Token> tokens = lex(templateFile);
        final AbstractSyntaxTree ast = parse(tokens);

        cache.put(cacheKey, ast);
        return (String) ast.run(arguments, locale, configuration);
    }

    private File findTemplateFileWith(final Locale locale) {
        final String templateFileURI = "templates" + File.separator + locale + File.separator + templateFilePath;
        final URL url = getClass().getClassLoader().getResource(templateFileURI);

        if (url != null) {
            return new File(url.getFile());
        }

        final Locale defaultLocale = configuration.getDefaultLocale();

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
