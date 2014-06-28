package com.highlight.template.renderer;

import org.stringtemplate.v4.ST;

import java.util.Map;


/* Temporary renderer */
public final class STRenderer implements Renderer {

    private final ST st;

    public STRenderer(final String templateAsString, final Map<String, Object> args) {
        this.st = new ST(templateAsString, '~', '~');
        this.addAllArguments(args);
    }

    private void addAllArguments(final Map<String, Object> args) {
        for (final Map.Entry<String, Object> argsEntry : args.entrySet())
            this.st.add(argsEntry.getKey(), argsEntry.getValue());
    }

    @Override
    public String render() {
        return this.st.render();
    }
}
