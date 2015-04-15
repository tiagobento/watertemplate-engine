package org.watertemplate.exception;

import org.watertemplate.Template;

import java.util.Locale;

public class RenderException extends TemplateException {
    public RenderException(Template t, Locale l, Throwable c) {
        super("Error rendering " + t.getClass() + " with locale " + l, c);
    }
}
