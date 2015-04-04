package org.watertemplate.interpreter.parser.abs;

import java.util.Map;

class TextCommand implements AbstractSyntaxTree.Command {

    private final String value;

    public TextCommand(final String value) {
        this.value = value;
    }

    @Override
    public Object run(final Map<String, Object> arguments) {
        return value;
    }
}
