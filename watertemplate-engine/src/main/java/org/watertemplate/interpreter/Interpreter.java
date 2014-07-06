package org.watertemplate.interpreter;

import java.util.Locale;

public interface Interpreter {
    final static Locale DEFAULT_LOCALE = Locale.US;
    final static char GENERAL_DELIMITER = '~';
    final static char COMMAND_DELIMITER = ':';

    public String interpret(final Locale locale);
}
