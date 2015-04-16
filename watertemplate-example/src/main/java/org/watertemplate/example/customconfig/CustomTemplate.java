package org.watertemplate.example.customconfig;

import org.watertemplate.Configuration;
import org.watertemplate.Template;

import java.util.Locale;

public abstract class CustomTemplate extends Template {
    private static final Configuration CUSTOM_CONFIGURATION = new Configuration() {
        @Override
        public Locale getDefaultLocale() {
            return Locale.GERMANY;
        }

        @Override
        public String applyTreatment(final String value) {
            return "evaluated by water:[" + value + "]";
        }
    };

    @Override
    protected Configuration getConfiguration() {
        return CUSTOM_CONFIGURATION;
    }
}
