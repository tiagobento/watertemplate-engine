package org.watertemplate.parser;

import org.watertemplate.TemplateArguments;
import org.watertemplate.TemplateException;

public class WaterParser implements Parser {

    private final String path;
    private final TemplateArguments templateArguments;

    public WaterParser(final String path, final TemplateArguments templateArguments) {
        this.templateArguments = templateArguments;
        this.path = path;
    }

    @Override
    public String parse() {
        throw new TemplateException("Not implemented.");
    }
}
