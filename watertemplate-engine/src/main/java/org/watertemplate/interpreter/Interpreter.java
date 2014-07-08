package org.watertemplate.interpreter;

import java.util.Locale;

public interface Interpreter {
    final static Locale DEFAULT_LOCALE = Locale.US;

    public String interpret(final Locale locale);
}
