package org.watertemplate.interpreter.parser.abs;

import org.watertemplate.TemplateMap;

public class AbstractSyntaxTree {

    private final Command command;

    public AbstractSyntaxTree(final Command command) {
        this.command = command;
    }

    public String run(final TemplateMap.Arguments arguments) {
        return (String) command.run(arguments);
    }

    static interface Command {
        public Object run(final TemplateMap.Arguments arguments);
    }
}
