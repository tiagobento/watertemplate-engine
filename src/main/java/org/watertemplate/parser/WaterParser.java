package org.watertemplate.parser;

import org.watertemplate.TemplateException;

import java.util.Map;

public class WaterParser implements Parser {

    private final Map<String, Object> arguments;
    private final String filePath;

    public WaterParser(final String filePath, final Map<String, Object> arguments) {
        this.arguments = arguments;
        this.filePath = filePath;
    }

    @Override
    public String parse() {
        throw new TemplateException("Not implemented.");
    }
}
