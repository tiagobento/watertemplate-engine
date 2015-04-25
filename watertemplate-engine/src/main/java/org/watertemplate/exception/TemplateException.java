package org.watertemplate.exception;

public abstract class TemplateException extends RuntimeException {
    public TemplateException(String s) {
        super(s);
    }

    protected TemplateException(String message, Throwable cause) {
        super(message, cause);
    }
}
