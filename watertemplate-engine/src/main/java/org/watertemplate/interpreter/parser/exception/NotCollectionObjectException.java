package org.watertemplate.interpreter.parser.exception;

import org.watertemplate.exception.TemplateException;
import org.watertemplate.interpreter.parser.AbstractSyntaxTree;

public class NotCollectionObjectException extends TemplateException {
    public NotCollectionObjectException(final AbstractSyntaxTree.Id id) {
        super("\"" + id.getFullId() + "\" is not a collection or was not added by the addCollection method.");
    }
}
