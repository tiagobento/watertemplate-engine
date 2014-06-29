package org.watertemplate.parser;

import org.watertemplate.TemplateException;

import java.util.Map;

public class WaterParser implements Parser {

    private final Map<String, Object> arguments;
    private final String templateFileURI;

    public WaterParser(final String templateFileURI, final Map<String, Object> arguments) {
        this.arguments = arguments;
        this.templateFileURI = templateFileURI;
    }

    @Override
    public String parse() {
        throw new TemplateException("Not implemented.");
    }
}
