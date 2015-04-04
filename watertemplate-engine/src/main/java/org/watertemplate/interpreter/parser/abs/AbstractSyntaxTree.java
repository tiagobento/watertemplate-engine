package org.watertemplate.interpreter.parser.abs;

import java.util.Map;

public class AbstractSyntaxTree {

    private final Command command;

    public AbstractSyntaxTree(final Command command) {
        this.command = command;
    }

    public String run(final Map<String, Object> args) {
        return (String) command.run(args);
    }

    static interface Command {
        public Object run(final Map<String, Object> arguments);
    }
}
