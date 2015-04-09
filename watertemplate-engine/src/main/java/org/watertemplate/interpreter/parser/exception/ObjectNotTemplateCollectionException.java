package org.watertemplate.interpreter.parser.exception;

import org.watertemplate.exception.TemplateException;
import org.watertemplate.interpreter.parser.AbstractSyntaxTree;

public class ObjectNotTemplateCollectionException extends TemplateException {
    public ObjectNotTemplateCollectionException(final AbstractSyntaxTree.Id id) {
        super("\"" + id.getFullId() + "\" is not a collection or was not added by the addCollection method.");
    }
}
