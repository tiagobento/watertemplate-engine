package org.watertemplate.interpreter.parser.abs;

import org.watertemplate.TemplateMap;

import java.util.Arrays;
import java.util.Collection;

import static org.watertemplate.interpreter.parser.abs.AbstractSyntaxTree.Command;

public class StatementsCommand implements Command {

    private Collection<Command> commands;

    public StatementsCommand(Command ... commands) {
        this.commands = Arrays.asList(commands);
    }

    @Override
    public Object run(TemplateMap.Arguments arguments) {
        StringBuilder sb = new StringBuilder();
        for (Command command : commands) {
            sb.append(command.run(arguments));
        }
        return sb.toString();
    }
}
