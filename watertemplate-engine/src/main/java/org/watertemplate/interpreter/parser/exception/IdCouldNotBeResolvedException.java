package org.watertemplate.interpreter.parser.exception;

import org.watertemplate.exception.TemplateException;
import org.watertemplate.interpreter.parser.AbstractSyntaxTree;

public class IdCouldNotBeResolvedException extends TemplateException {
    public IdCouldNotBeResolvedException(final String fullId) {
        super("\"" + fullId + "\" could not be resolved.");
    }
}
