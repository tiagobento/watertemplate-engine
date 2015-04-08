package org.watertemplate.interpreter;

import org.watertemplate.TemplateMap;
import org.watertemplate.exception.TemplateException;
import org.watertemplate.interpreter.lexer.Lexer;
import org.watertemplate.interpreter.lexer.Token;
import org.watertemplate.interpreter.parser.RecursiveDescentParser;
import org.watertemplate.interpreter.parser.abs.AbstractSyntaxTree;
import org.watertemplate.interpreter.reader.Reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class WaterInterpreter implements Interpreter {

    private final TemplateMap.Arguments arguments;
    private final String templateFilePath;

    public WaterInterpreter(final String templateFilePath, final TemplateMap.Arguments arguments) {
        this.templateFilePath = templateFilePath;
        this.arguments = arguments;
    }

    @Override
    public String interpret(final Locale locale) {
        final File templateFile = findTemplateFileWith(locale);

        final List<Token> tokens = lex(templateFile);

        AbstractSyntaxTree abs = new RecursiveDescentParser(tokens).buildAbs();
        return (String) abs.run(arguments);
    }

    private List<Token> lex(final File templateFile) {
        final Lexer lexer = new Lexer();

        final Reader reader = new Reader(templateFile);
        reader.readExecuting(lexer::accept);

        final List<Token> tokens = lexer.getTokens();
        tokens.add(Token.END_OF_INPUT);
        return tokens;
    }

    private File findTemplateFileWith(final Locale locale) {
        final String templateFileURI = "templates" + File.separator + locale + File.separator + templateFilePath;
        final URL url = getClass().getClassLoader().getResource(templateFileURI);

        if (url != null) {
            return new File(url.getFile());
        }

        if (!locale.equals(DEFAULT_LOCALE)) {
            return findTemplateFileWith(DEFAULT_LOCALE);
        }

        throw new TemplateException(new FileNotFoundException(templateFilePath));
    }
}
