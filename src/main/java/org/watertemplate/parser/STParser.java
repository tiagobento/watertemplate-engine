package org.watertemplate.parser;

import org.stringtemplate.v4.ST;

import java.util.Map;


/* Temporary parser */
public final class STParser implements Parser {

    private final ST st;

    public STParser(final String templateAsString, final Map<String, Object> args) {
        this.st = new ST(templateAsString, DELIMITER, DELIMITER);
        this.addAllArguments(args);
    }

    private void addAllArguments(final Map<String, Object> args) {
        for (final Map.Entry<String, Object> argsEntry : args.entrySet())
            this.st.add(argsEntry.getKey(), argsEntry.getValue());
    }

    @Override
    public String parse() {
        return this.st.render();
    }
}
