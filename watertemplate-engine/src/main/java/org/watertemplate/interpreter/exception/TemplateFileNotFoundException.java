package org.watertemplate.interpreter.exception;

import org.watertemplate.exception.TemplateException;

public class TemplateFileNotFoundException extends TemplateException {
    public TemplateFileNotFoundException(String templateFilePath) {
        super("\"" + templateFilePath + "\" not present in classpath");
    }
}
