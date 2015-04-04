package org.watertemplate.interpreter.parser.abs;

import org.watertemplate.exception.TemplateException;

import java.util.Map;

class IdCommand implements AbstractSyntaxTree.Command {

    private final String propertyKey;
    private final AbstractSyntaxTree.Command nestedIdCommand;

    public IdCommand(final String propertyKey) {
        this.propertyKey = propertyKey;
        this.nestedIdCommand = null;
    }

    public IdCommand(final String propertyKey, final IdCommand nestedIdCommand) {
        this.propertyKey = propertyKey;
        this.nestedIdCommand = nestedIdCommand;
    }

    public Object run(final Map<String, Object> arguments) {
        if (nestedIdCommand instanceof IdCommand) try {
            return nestedIdCommand.run((Map) arguments.get(propertyKey));
        } catch (ClassCastException e) {
            throw new TemplateException("Property \"" + propertyKey + "\" contains no nested properties.");
        }

        if (arguments.containsKey(propertyKey)) {
            return arguments.get(propertyKey);
        }

        return propertyKey;
    }

}
