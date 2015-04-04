package org.watertemplate.interpreter.parser.abs;

import org.watertemplate.TemplateMap;
import org.watertemplate.exception.TemplateException;

import java.util.Map;

import static org.watertemplate.TemplateMap.Arguments;
import static org.watertemplate.TemplateMap.TemplateObject;

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

    public Object run(final Arguments arguments) {
        if (nestedIdCommand instanceof IdCommand) try {
            Arguments nestedArguments = ((TemplateObject) arguments.getObject(propertyKey)).map();
            return nestedIdCommand.run(nestedArguments);
        } catch (ClassCastException e) {
            throw new TemplateException("Property \"" + propertyKey + "\" contains no nested properties.");
        }

        if (arguments.getObject(propertyKey) != null) {
            return arguments.getObject(propertyKey);
        }

        return propertyKey;
    }

}
