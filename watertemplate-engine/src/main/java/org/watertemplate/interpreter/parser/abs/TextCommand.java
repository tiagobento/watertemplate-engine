package org.watertemplate.interpreter.parser.abs;

import org.watertemplate.TemplateMap;

class TextCommand implements AbstractSyntaxTree.Command {

    private final String value;

    public TextCommand(final String value) {
        this.value = value;
    }

    @Override
    public Object run(final TemplateMap.Arguments arguments) {
        return value;
    }
}
