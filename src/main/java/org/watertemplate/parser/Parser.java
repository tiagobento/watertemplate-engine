package org.watertemplate.parser;

import java.util.Locale;

public interface Parser {
    final static Locale DEFAULT_LOCALE = Locale.US;
    final static char DELIMITER = '~';

    public String parse(final Locale locale);
}
