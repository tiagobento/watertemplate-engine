package org.watertemplate.parser;

import org.apache.commons.io.FileUtils;
import org.stringtemplate.v4.ST;
import org.watertemplate.TemplateException;

import java.io.File;
import java.io.IOException;
import java.util.Map;


/* Temporary parser */
public final class STParser implements Parser {

    private final ST st;

    public STParser(final String templateFileURI, final Map<String, Object> args) {
        try {
            this.st = new ST(FileUtils.readFileToString(new File(templateFileURI)), DELIMITER, DELIMITER);
            this.addAllArguments(args);
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }

    private void addAllArguments(final Map<String, Object> args) {
        for (final Map.Entry<String, Object> argsEntry : args.entrySet())
            this.st.add(argsEntry.getKey(), argsEntry.getValue());
    }

    @Override
    public String parse() {
        return this.st.render();
    }
}
