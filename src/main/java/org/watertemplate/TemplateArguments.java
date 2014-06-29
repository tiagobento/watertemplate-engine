package org.watertemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateArguments {
    final Map<String, Object> map = new HashMap<>();

    protected final void add(final String key, final Object value) {
        this.map.put(key, value);
    }
}
