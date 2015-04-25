package org.watertemplate.interpreter;

import org.watertemplate.TemplateMap;
import org.watertemplate.interpreter.lexer.Lexer;
import org.watertemplate.interpreter.lexer.Token;
import org.watertemplate.interpreter.parser.AbstractSyntaxTree;
import org.watertemplate.interpreter.parser.Parser;
import org.watertemplate.interpreter.reader.Reader;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;

public abstract class WaterInterpreter {

    protected final String templateFilePath;
    protected final Locale defaultLocale;

    public WaterInterpreter(final String templateFilePath, final Locale defaultLocale) {
        this.templateFilePath = templateFilePath;
        this.defaultLocale = defaultLocale;
    }

    public abstract String string(final TemplateMap.Arguments arguments, final Locale locale);

    protected AbstractSyntaxTree parse(final List<Token> tokens) {
        return new Parser(tokens).buildAbstractSyntaxTree();
    }

    protected List<Token> lex(final File templateFile) {
        final Lexer lexer = new Lexer();

        final Reader reader = new Reader(templateFile);
        reader.readExecuting(lexer::accept);

        final List<Token> tokens = lexer.getTokens();
        tokens.add(Token.END_OF_INPUT);
        return tokens;
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
