package org.watertemplate.interpreter.parser.exception;

public class NoMoreTokensOnStreamException extends ParseException {
    public NoMoreTokensOnStreamException() {
        super("There is something missing on your template. Are you sure you've finished writing it?");
    }
}
