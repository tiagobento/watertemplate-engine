package org.watertemplate.interpreter;

import org.watertemplate.exception.TemplateException;
import org.watertemplate.interpreter.lexer.Lexer;
import org.watertemplate.interpreter.lexer.Token;
import org.watertemplate.interpreter.parser.RecursiveDescentParser;
import org.watertemplate.interpreter.reader.TemplateReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WaterInterpreter implements Interpreter {

    private final Map<String, Object> arguments;
    private final String templateFilePath;

    public WaterInterpreter(final String templateFilePath, final Map<String, Object> arguments) {
        this.templateFilePath = templateFilePath;
        this.arguments = arguments;
    }

    @Override
    public String interpret(final Locale locale) {
        final Lexer lexer = new Lexer();

        final TemplateReader reader = new TemplateReader(getTemplateFile(locale));
        reader.readExecuting(lexer::accept);

        final List<Token> tokens = lexer.getTokens();
        tokens.add(Token.END_OF_INPUT);

        final RecursiveDescentParser parser = new RecursiveDescentParser(tokens);
            parser.parse();

        return "";
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
