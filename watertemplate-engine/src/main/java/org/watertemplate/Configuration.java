package org.watertemplate;

import java.util.Locale;

public interface Configuration {

    static final Configuration DEFAULT = new Configuration() {
    };

    default Locale getDefaultLocale() {
        return Locale.US;
    }

    default String applyTreatment(final String value) {
        return value;
    }
}
