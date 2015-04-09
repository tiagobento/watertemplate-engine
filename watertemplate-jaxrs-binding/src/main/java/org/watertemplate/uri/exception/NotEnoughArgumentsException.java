package org.watertemplate.uri.exception;

import org.watertemplate.exception.TemplateException;

public class NotEnoughArgumentsException extends TemplateException {
    public NotEnoughArgumentsException(final String staticPath) {
        super("After formatting the path was \"" + staticPath + "\"");
    }
}
